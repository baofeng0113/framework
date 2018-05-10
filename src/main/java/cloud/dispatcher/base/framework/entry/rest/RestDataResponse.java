package cloud.dispatcher.base.framework.entry.rest;

import java.io.Serializable;

import lombok.*;

@AllArgsConstructor
@ToString
@NoArgsConstructor
public class RestDataResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter @Setter private Object data;

    @Getter @Setter private RestMetaResponse meta;
}
