package com.example.wmsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wmsbackend.converter.StockTransactionConverter;
import com.example.wmsbackend.entity.Item;
import com.example.wmsbackend.entity.QueryPageParam;
import com.example.wmsbackend.entity.ResponsePage;
import com.example.wmsbackend.entity.StockTransaction;
import com.example.wmsbackend.entity.vo.StockTransactionRankingVo;
import com.example.wmsbackend.entity.vo.StockTransactionVo;
import com.example.wmsbackend.mapper.StockTransactionMapper;
import com.example.wmsbackend.service.ItemService;
import com.example.wmsbackend.service.StockTransactionService;
import com.example.wmsbackend.util.ApiResponse;
import com.example.wmsbackend.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockTransactionServiceImpl extends ServiceImpl<StockTransactionMapper, StockTransaction>
        implements StockTransactionService {
    private final StockTransactionConverter stockTransactionConverter;
    private final ItemService itemService;
    private final StockTransactionMapper stockTransactionMapper;

    @Override
    public ResponsePage<StockTransactionVo> getStockTransactionPageData(QueryPageParam queryPageParam) {
        Page<StockTransaction> stockTransactionPage = new Page<>(queryPageParam.getCurrent(), queryPageParam.getSize());

        return PaginationUtil.getPaginatedData(
                stockTransactionPage,
                (p) -> stockTransactionMapper.selectPage(p, null),
                stockTransactionConverter::toVO
        );
    }

    @Override
    public List<StockTransactionRankingVo> getStockTransactionRanking(LocalDate startDate, LocalDate endDate) {
        // 获取数据库查询结果
        List<StockTransactionRankingVo> stockTransactions = stockTransactionMapper.getTopStockTransactions(startDate, endDate);

        // 返回转换后的数据
        return stockTransactions.stream()
                .map(stockTransaction -> stockTransactionConverter.toRankingVO(
                        stockTransaction.getItemName(),
                        stockTransaction.getTotalIn(),
                        stockTransaction.getTotalOut()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> addStockTransaction(StockTransactionVo stockTransactionVo) {
        StockTransaction stockTransaction = stockTransactionConverter.toPO(stockTransactionVo);
        LambdaUpdateWrapper<Item> itemLambdaUpdateWrapper = new LambdaUpdateWrapper<>();

        // 获取原始库存
        Long originalStock = itemService.getById(stockTransaction.getItemId()).getStock();

        switch (stockTransaction.getTransactionType()) {
            case "in":
                // 入库
                itemLambdaUpdateWrapper.eq(Item::getId, stockTransaction.getItemId())
                        .set(Item::getStock, originalStock + stockTransaction.getQuantity());
                this.save(stockTransaction); // 保存交易记录
                itemService.update(itemLambdaUpdateWrapper); // 更新库存
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("入库成功", true));

            case "out":
                // 出库
                if (stockTransaction.getQuantity() > originalStock) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("库存不足", false));
                }
                itemLambdaUpdateWrapper.eq(Item::getId, stockTransaction.getItemId())
                        .set(Item::getStock, originalStock - stockTransaction.getQuantity());
                this.save(stockTransaction); // 保存交易记录
                itemService.update(itemLambdaUpdateWrapper); // 更新库存
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("出库成功", true));

            case "transfer":
                // 调拨
                // 检查库存是否足够
                if (stockTransaction.getQuantity() > originalStock) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("库存不足", false));
                }

                // 更新源仓库库存：减少数量
                itemLambdaUpdateWrapper.eq(Item::getId, stockTransaction.getItemId())
                        .set(Item::getStock, originalStock - stockTransaction.getQuantity());
                itemService.update(itemLambdaUpdateWrapper); // 更新源仓库库存

                // 获取目标仓库的信息
                LambdaQueryWrapper<Item> itemLambdaQueryWrapper = new LambdaQueryWrapper<>();
                itemLambdaQueryWrapper.eq(Item::getItemName, removeParentheses(stockTransactionVo.getItemName()) + "(" + stockTransactionVo.getTargetWarehouseName() + ")");
                Item targetItem = itemService.getOne(itemLambdaQueryWrapper);


                if (targetItem == null) {
                    // 如果目标仓库没有该物品，可能需要新建物品记录，或者返回错误
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("目标仓库中没有该物品", false));
                }

                // 更新目标仓库库存：增加数量
                Long targetStock = targetItem.getStock();
                itemLambdaUpdateWrapper.clear();
                itemLambdaUpdateWrapper
                        .eq(Item::getId, targetItem.getId())
                        .set(Item::getStock, targetStock + stockTransaction.getQuantity());
                itemService.update(itemLambdaUpdateWrapper); // 更新目标仓库库存

                this.save(stockTransaction); // 保存调拨交易记录
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("调拨成功", true));

            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("无效的交易类型", false));
        }
    }

    // 用于去除字符串末尾的括号及其中内容
    private String removeParentheses(String input) {
        // 正则：去掉末尾括号及括号里的内容
        return input.replaceAll("\\s?\\([^)]+\\)$", "");
    }


}
