package org.lhyf.config.server.model.Vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/****
 * @author YF
 * @date 2018-07-17 18:36
 * @desc JsonRootBean
 *
 **/
@Getter
@Setter
@ToString
public class EnvNameSpaceVo {
    private Integer envId;
    private List<NameSpaceItemVo> ns;


}