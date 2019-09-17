package net.dgg.yk.platform.backend.stack.advance;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.util.Iterator;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class IteratorSource<D> {

    private Iterator<D> iterator;
    private Closeable[] closeables;

}
