package org.lhyf.config.pojo;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_namespace")
public class NameSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "env_id")
    private Integer envId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "update_by")
    private String updateBy;

    private String intro;

    @Column(name = "space_code")
    private String spaceCode;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return env_id
     */
    public Integer getEnvId() {
        return envId;
    }

    /**
     * @param envId
     */
    public void setEnvId(Integer envId) {
        this.envId = envId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return create_by
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return update_by
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * @return intro
     */
    public String getIntro() {
        return intro;
    }

    /**
     * @param intro
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * @return space_code
     */
    public String getSpaceCode() {
        return spaceCode;
    }

    /**
     * @param spaceCode
     */
    public void setSpaceCode(String spaceCode) {
        this.spaceCode = spaceCode;
    }
}