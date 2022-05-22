package io.springbatch.springbatchlecture.flatfileitemreader;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class CustomerFiledSetMapper implements FieldSetMapper<CustomerDto> {

    @Override
    public CustomerDto mapFieldSet(FieldSet fieldSet) {
        if (fieldSet == null) {
            return null;
        }

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(fieldSet.readString(0));
        customerDto.setAge(fieldSet.readInt(1));
        customerDto.setYear(fieldSet.readString(2));
        return customerDto;
    }
}
