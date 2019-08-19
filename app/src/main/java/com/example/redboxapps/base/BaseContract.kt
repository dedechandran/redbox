package com.example.redboxapps.base

interface BaseContract {
    interface View
    interface Presenter<T>{
        fun onAttach(view : T)
        fun onDetach()
    }
}