package Async;

import android.os.AsyncTask;

import Structures.StructPerson;
import Web.Commands;

public class AsyncDelete extends AsyncTask<Integer, Void, Boolean> {
    private Runnable startAction;
    private OnDataReceivedListener onDataReceivedListener;

    @Override
    protected void onPreExecute() {
        if (startAction != null) {
            startAction.run();
        }
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        return Commands.delete(integers[0]);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (onDataReceivedListener != null) {
            onDataReceivedListener.OnDataReceived(result);
        }
    }

    public AsyncDelete setOnDataReceivedListener(OnDataReceivedListener onDataReceivedListener) {
        this.onDataReceivedListener = onDataReceivedListener;
        return this;
    }

    public AsyncDelete setStartAction(Runnable startAction) {
        this.startAction = startAction;
        return this;
    }

    public interface OnDataReceivedListener {
        void OnDataReceived(boolean result);
    }
}
