package org.lhyf.config.model.Vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/****
 * @author YF
 * @date 2018-07-17 18:27
 * @desc TestVo
 *
 **/
@Getter
@Setter
@ToString
public class NameSpaceItemVo {
    private Integer id;

    private List<Integer> item;


}
