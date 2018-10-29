package com.moviesdb.moviesdbmvvm.ui.base.activity;

import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

public class MenuCommandBindings implements IMenuCallbackListener {

    public enum EnableBinding{
        none, visible,enabled
    }

    private HashMap<Integer,MenuBinding> mCommandMap = new HashMap<>();
    private BaseActivity baseActivity;

    public MenuCommandBindings(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        baseActivity.setMenuCallbackListener(this);
    }

    public MenuCommandBindings() {

    }

    public void addBinding(int menuId, Command cmd, EnableBinding enableBinding){
        MenuBinding menuBinding = new MenuBinding(menuId,cmd,enableBinding);
        mCommandMap.put(menuId, menuBinding);
        cmd.addOnPropertyChangedCallback(propertyChangedCallback);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        for(Map.Entry<Integer, MenuBinding> entry: mCommandMap.entrySet()){
            MenuItem menuItem = menu.findItem(entry.getKey());
            if(menuItem==null){
                continue;
            }
            if(entry.getValue().enableBinding == EnableBinding.enabled){
                menuItem.setEnabled(entry.getValue().command.isEnabled());
            }else if(entry.getValue().enableBinding == EnableBinding.visible){
                menuItem.setVisible(entry.getValue().command.isEnabled());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(int menuItemId) {
        MenuBinding menuBinding = mCommandMap.get(menuItemId);
        if(menuBinding!=null)
            menuBinding.command.execute();
        return true;
    }

    private final android.databinding.Observable.OnPropertyChangedCallback propertyChangedCallback = new android.databinding.Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(android.databinding.Observable sender, int propertyId) {
            if(baseActivity!=null)
                baseActivity.invalidateOptionsMenu();
        }
    };

    private class MenuBinding{
        public int mMenuId;
        public Command command;
        public EnableBinding enableBinding;

        public MenuBinding(int mMenuId, Command command, EnableBinding enableBinding) {
            this.mMenuId = mMenuId;
            this.command = command;
            this.enableBinding = enableBinding;
        }
    }
}
