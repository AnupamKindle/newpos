package com.kindlebit.pos.utill;


import com.kindlebit.pos.dto.NumberOfTimeOrdersDTO;

import java.util.Comparator;

public class NumberOfTimeOrdersDTOComparator implements Comparator<NumberOfTimeOrdersDTO> {


    @Override
    public int compare(NumberOfTimeOrdersDTO o1, NumberOfTimeOrdersDTO o2) {

        if(o1.getTotalOrder()>o2.getTotalOrder())
        {
            return -1;
        } else if (o1.getTotalOrder()<o2.getTotalOrder()) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
