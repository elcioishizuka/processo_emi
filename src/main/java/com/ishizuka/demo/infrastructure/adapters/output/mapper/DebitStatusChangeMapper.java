package com.ishizuka.demo.infrastructure.adapters.output.mapper;

import com.ishizuka.demo.domain.model.DebitStatusChange;
import com.ishizuka.demo.infrastructure.adapters.output.dto.DebitStatusChangeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DebitStatusChangeMapper {

    DebitStatusChangeDto toDebitStatusChangeDto (DebitStatusChange debitStatusChange);

}
