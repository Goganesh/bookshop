package com.goganesh.bookshop.webui.client.mapper;

import com.goganesh.bookshop.model.domain.BalanceTransaction;
import com.goganesh.bookshop.webui.client.dto.BalanceTransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BalanceTransactionMapper {

    @Mapping(target = "description",
            source = "balanceTransaction.description")
    @Mapping(target = "sum",
            expression = "java( (balanceTransaction.getBook() == null ? \"+\" : \"-\") + String.format(\"%s р.\", balanceTransaction.getValue()) )")
    @Mapping(target = "time",
            source = "balanceTransaction.time",
            dateFormat = "dd-MM-yyyy HH:mm:ss"
    )
    BalanceTransactionDto toDto(BalanceTransaction balanceTransaction);
}
