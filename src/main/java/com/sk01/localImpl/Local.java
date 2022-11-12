package com.sk01.localImpl;

import com.sk01.StorageManager;

public class Local {

    static {
        StorageManager.registerStorage(new LocalCreate(), new LocalStorage(), new LocalSearch(), new LocalOperations());
    }

}
