package net.micode.fileexplorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CategoryFileListAdaptor extends BaseAdapter{

	public int getCount() {
		// TODO Auto-generated method stub
		return mFileNameList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View view, ViewGroup parent) {
		FileInfo fileInfo = mFileNameList.get(position);
		if (view == null) {
			view = mFactory.inflate(R.layout.category_file_browser_item, parent, false);
		}
        FileListItem.setupFileListItemInfo(mContext, view, fileInfo, mFileIcon,
                mFileViewInteractionHub);
        view.findViewById(R.id.category_file_checkbox_area).setOnClickListener(
                new FileListItem.FileItemOnClickListener(mContext, mFileViewInteractionHub));
        return view;
	}
	
	public void changeCursor(Cursor cursor) {
        mFileNameList.clear();
        FileInfo fileInfo;
		while (cursor.moveToNext()) {
			fileInfo = new FileInfo();
			fileInfo.dbId = cursor.getLong(FileCategoryHelper.COLUMN_ID);
			fileInfo.filePath = cursor
					.getString(FileCategoryHelper.COLUMN_PATH);
			fileInfo.fileName = Util.getNameFromFilepath(fileInfo.filePath);
			fileInfo.fileSize = cursor.getLong(FileCategoryHelper.COLUMN_SIZE);
			//fileInfo.ModifiedDate = cursor
					//.getLong(FileCategoryHelper.COLUMN_DATE);
			fileInfo.ModifiedDate = new File(fileInfo.filePath).lastModified();
			mFileNameList.add(fileInfo);
		}
    }
    private final LayoutInflater mFactory;

    private FileViewInteractionHub mFileViewInteractionHub;

    private FileIconHelper mFileIcon;

    private ArrayList<FileInfo> mFileNameList = new ArrayList< FileInfo>();

    private Context mContext;

    public CategoryFileListAdaptor(Context context,
            FileViewInteractionHub f, FileIconHelper fileIcon) {
        mFactory = LayoutInflater.from(context);
        mFileViewInteractionHub = f;
        mFileIcon = fileIcon;
        mContext = context;
    }

    /*public View newView(Context context, ViewGroup parent) {
        return mFactory.inflate(R.layout.category_file_browser_item, parent, false);
    }*/
	public void remove(String file) {
		for (FileInfo f : mFileNameList) {
			if (f.filePath.equals(file)) {
				mFileNameList.remove(f);
				return;
			}
		}

	}
    public Collection<FileInfo> getAllFiles() {
        return mFileNameList;
    }

    public FileInfo getFileItem(int pos) {
    	if (pos >= mFileNameList.size())
    		return null;
        FileInfo fileInfo = mFileNameList.get(pos);
        return fileInfo;
    }
}
