package org.lhyf.config.model.Vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/****
 * @author YF
 * @date 2018-07-15 20:52
 * @desc ItemVo
 *
 **/
@Getter
@Setter
public class ItemVo {
    private Integer id;

    private String property;

    private String value;

    private String intro;

    private Date updateTime;

    private String updateBy;
}
