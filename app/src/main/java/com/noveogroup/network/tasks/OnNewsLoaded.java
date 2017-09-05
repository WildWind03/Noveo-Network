package com.noveogroup.network.tasks;

import com.noveogroup.network.model.News;

import java.util.List;

public interface OnNewsLoaded {
    void onTaskComplete(List<News> news);
}
