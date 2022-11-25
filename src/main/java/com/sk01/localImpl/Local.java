package com.sk01.localImpl;

import com.sk01.StorageManager;

public class Local {

        static{
            StorageManager.registerStorage(new LocalCreate(), new LocalOperations(), new LocalSearch(), new LocalStorage());
        }

        private Local(){

        }
}
