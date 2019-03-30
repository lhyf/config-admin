package org.lhyf.config.model.Vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/****
 * @author YF
 * @date 2018-07-14 17:46
 * @desc EnvVo
 *
 **/
@Getter
@Setter
public class EnvVo {

    private Integer id;

    private String name;

    private Date updateTime;

    private String appCode;

    private String updateBy;

    private String intro;

    private List<NameSpaceVo> nameSpaces;
}
