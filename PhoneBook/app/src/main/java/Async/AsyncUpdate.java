package Async;

import android.os.AsyncTask;

import Structures.StructPerson;
import Web.Commands;

public class AsyncUpdate extends AsyncTask<StructPerson, Void, Boolean> {
    private Runnable startAction;
    private OnDataReceivedListener onDataReceivedListener;

    @Override
    protected void onPreExecute() {
        if (startAction != null) {
            startAction.run();
        }
    }

    @Override
    protected Boolean doInBackground(StructPerson... structPersons) {
        return Commands.update(structPersons[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (onDataReceivedListener != null) {
            onDataReceivedListener.OnDataReceived(result);
        }
    }

    public AsyncUpdate setOnDataReceivedListener(OnDataReceivedListener onDataReceivedListener) {
        this.onDataReceivedListener = onDataReceivedListener;
        return this;
    }

    public AsyncUpdate setStartAction(Runnable startAction) {
        this.startAction = startAction;
        return this;
    }

    public interface OnDataReceivedListener {
        void OnDataReceived(boolean result);
    }
}
