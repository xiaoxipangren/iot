package com.nationalchip.iot.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/23/18 12:46 PM
 * @Modified:
 */
public class PageResponse<T extends ResourceSupport> {
    private final List<T> content;
    private final long total;
    private final int size;
    private final int offset;



    public PageResponse( @NotNull final List<T> content,final long total,final int offset) {
        this.size = content.size();
        this.total = total;
        this.content = content;
        this.offset=offset;
    }


    public int getSize() {
        return size;
    }


    public long getTotal() {
        return total;
    }

    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    public int getOffset() {
        return offset;
    }
}
