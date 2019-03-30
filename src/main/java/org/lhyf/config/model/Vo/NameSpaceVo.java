package org.lhyf.config.model.Vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/****
 * @author YF
 * @date 2018-07-15 12:06
 * @desc NameSpaceVo
 *
 **/
@Getter
@Setter
public class NameSpaceVo {

    private Integer id;

    private String name;

    private Date updateTime;

    private String updateBy;

    private String intro;

    private String spaceCode;

    private List<ItemVo> items;

}
