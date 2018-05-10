package cloud.dispatcher.base.framework.entry.rest;

import java.io.Serializable;

import lombok.*;

@AllArgsConstructor
@ToString
@NoArgsConstructor
public class RestMetaResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter @Setter private String[] args;

    @Getter @Setter private int code;

    @Getter @Setter private String message;
}
