package com.holderzone.android.holderpick.screen.ui.base.modle;


import com.holderzone.android.holderpick.screen.ui.base.modle.cache.CacheManager;
import com.holderzone.android.holderpick.screen.ui.base.modle.file.FileManager;
import com.holderzone.android.holderpick.screen.ui.base.modle.network.NetworkManager;

/**
 * 数据仓库
 *
 * @author www
 * @date 2018/11/8 18:00.
 */

public interface Repository extends CacheManager, FileManager, NetworkManager {
}
