package org.lhyf.config.model.Vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/****
 * @author YF
 * @date 2018-07-16 12:03
 * @desc PubNameSpaceVo
 *
 **/
@Getter
@Setter
public class PubNameSpaceVo {
    private Integer id;

    private String name;

    private String intro;

    private List<PubItemVo> items;
}
