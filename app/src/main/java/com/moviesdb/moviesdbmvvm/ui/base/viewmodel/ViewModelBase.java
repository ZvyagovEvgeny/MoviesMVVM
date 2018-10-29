package com.moviesdb.moviesdbmvvm.ui.base.viewmodel;

import android.databinding.BaseObservable;

import com.moviesdb.moviesdbmvvm.ui.base.activity.Command;

import java.util.ArrayList;
import java.util.List;

public abstract class ViewModelBase extends BaseObservable {

    public List<CommandVM> mCommands;

    protected void refreshCommands(){
        for(CommandVM cmd:mCommands){
            cmd.refresh();
        }
    }

    public abstract class CommandVM extends Command{
        public void refresh(){}

        public CommandVM(){
            if(mCommands==null)
                mCommands = new ArrayList<>();
            mCommands.add(this);
        }
    }

}
