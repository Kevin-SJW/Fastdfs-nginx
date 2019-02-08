package com.siemens.fastdfs.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * fastdfs
 * 加入审计功能 AbstractAuditingEntity
 */
@Setter
@Getter
@Entity
@Table(name = "fastdfs")
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "fastdfs")

public class Fastdfs extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dept_seq")
    @SequenceGenerator(name = "dept_seq", sequenceName = "seq_dept", allocationSize = 1)
    @Column(name = "ID", nullable = false)

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "图片名称")
    private String fileName;

    @ApiModelProperty(value = "组名")
    private String groupName;

    @ApiModelProperty(value = "图片路径")
    private String imagePath;


}

