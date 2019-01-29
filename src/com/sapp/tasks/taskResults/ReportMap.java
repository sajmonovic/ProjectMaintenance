package com.sapp.tasks.taskResults;

import com.sapp.drawings.Drawing;

import java.util.LinkedHashMap;
import java.util.Map;

public class ReportMap {

    private Map<Drawing, TaskResult> map = new LinkedHashMap<>();

    public Map<Drawing, TaskResult> getMap() {
        return map;
    }

    public void setMap(Map<Drawing, TaskResult> map) {
        this.map = map;
    }

    public void put(Drawing key, TaskResult value) {
        map.put(key, value);
    }

    public TaskResult get(Drawing key) {
        return map.get(key);
    }
}
