package com.goganesh.bookshop.webapi.client.mapper;

import com.goganesh.bookshop.model.domain.BalanceTransaction;
import com.goganesh.bookshop.webapi.client.dto.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZoneOffset;

@Mapper(componentModel = "spring", imports = {ZoneOffset.class})
public interface BalanceTransactionMapper {

    @Mapping(target = "description",
            source = "balanceTransaction.description")
    @Mapping(target = "value",
            expression = "java( (balanceTransaction.getBook() == null ? \"+\" : \"-\") + String.format(\"%s р.\", balanceTransaction.getValue()) )")
    @Mapping(target = "time",
            expression = "java( balanceTransaction.getTime().toEpochSecond(ZoneOffset.UTC) )"
    )
    TransactionDto toDto(BalanceTransaction balanceTransaction);
}
