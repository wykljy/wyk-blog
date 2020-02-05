package com.wangyakun.boot.wykblog.model.vo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.io.Serializable;
/**
 * @ClassName IndexPageVO
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/2/1 19:03
 * @Version 1.0
 **/
@Data
//忽略不存在的字段
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexPageVO implements Serializable {
    private static final long serialVersionUID=1L;

    private int vipCount;

    private int articleCount;

    private int commentCount;
}
