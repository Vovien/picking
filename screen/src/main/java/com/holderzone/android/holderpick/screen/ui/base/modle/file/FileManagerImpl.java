package com.holderzone.android.holderpick.screen.ui.base.modle.file;

/**
 * 文件实现类
 *
 * @author www
 * @date 2018/11/9 10:17.
 */

public class FileManagerImpl implements FileManager {

    private volatile static FileManagerImpl sInstance;

    /**
     * 单例模式
     */
    public static FileManagerImpl getInstance() {
        if (sInstance == null) {
            synchronized (FileManagerImpl.class) {
                if (sInstance == null) {
                    sInstance = new FileManagerImpl();
                }
            }
        }
        return sInstance;
    }
    private FileManagerImpl() {
    }
}
