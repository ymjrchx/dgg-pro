package net.dgg.gspt.dqc.utils.es.stack;

import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李程 on 2018/10/22.
 */
public class StoreConsole implements Console {

    private List<SearchHit> datas = new ArrayList<>();

    private boolean isStop = false;

    @Override
    public void write(SearchHit object) {
        datas.add(object);
    }

    @Override
    public void stop() {
        isStop = true;
    }

    @Override
    public boolean isStop() {
        return isStop;
    }

    @Override
    public List<SearchHit> getList() {
        return datas;
    }
}