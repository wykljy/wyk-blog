package com.wangyakun.boot.wykblog.model.vo;
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
public class IndexPageVO implements Serializable {
    private static final long serialVersionUID=1L;

    private int vipCount;

    private int articleCount;

    private int commentCount;
}
