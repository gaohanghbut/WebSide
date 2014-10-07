package cn.hang.mvc.view;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.hang.mvc.RequestContext;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;

/**
 * view为输出流
 * 
 * @author hang.gao Initial Created at 2014年5月28日
 */
public class StreamRender implements Render {
    
    private final Log log = LogFactory.getLog(StreamRender.class);

    @Override
    public void rend(RequestContext ctx, Annotation annotation, Object view) {
        Preconditions.checkNotNull(ctx);
        if (view == null) {
            return;
        }
        if (view instanceof InputStream) {
            InputStream in = null;
            try {
                in = (InputStream) view;
                ByteStreams.copy(in, ctx.getHttpServletResponse().getOutputStream());
            } catch (IOException e) {
                throw new SerializeException(e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        if (log.isErrorEnabled()) {
                            log.error("Close the stream error", e);
                        }
                    }
                }
            }
        }
    }

}
