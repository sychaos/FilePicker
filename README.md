# FilePicker

## Sample Code Common

## Screenshot

![](gif/display.gif)

## Usage

### Step 1. add this to manifest

```xml
     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

     <activity android:name="cn.filepicker.common.FilePickerActivity"/>
```

### Step 2. override onActivityResult like this

```Java
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (resultCode == RESULT_OK) {
             if (requestCode == 1004 || requestCode == 1005) {
                 files = (List<FileItem>) data.getSerializableExtra(FilePickerBaseActivity.EXTRA_DATA);
             }
         }

     }
```

### Step 3. start

```Java
    FilePicker.builder()
        .withActivity(MainActivity.this)
        .withRequestCode(1004)
        .withSelectedFiles(files)
        .withTitleColor(R.color.filepicker_colorPrimary)
        .build();
```

## Advance Usage(Customized Adapter)

### Step 1. new an Adapter extends BaseFileAdapter like this

```Java
    public class CommonFileAdapter extends BaseFileAdapter {

        public static final int TYPE_DOC = 1001;
        public static final int TYPE_FOLDER = 1002;
        public static final int TYPE_DIVIDER = 1003;
        public static final int TYPE_EMPTYE = 1004;

        public CommonFileAdapter(Context mContext) {
            super(mContext);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            ViewGroup viewGroup = null;
            switch (viewType) {
                case TYPE_DOC:
                    viewGroup = (ViewGroup) mInflater.inflate(R.layout.item_doc, parent, false);
                    break;
                case TYPE_FOLDER:
                    viewGroup = (ViewGroup) mInflater.inflate(R.layout.item_folder, parent, false);
                    break;
                case TYPE_DIVIDER:
                    viewGroup = (ViewGroup) mInflater.inflate(R.layout.common_divider, parent, false);
                    break;
                case TYPE_EMPTYE:
                    viewGroup = (ViewGroup) mInflater.inflate(R.layout.empty_view, parent, false);
                    break;
            }
            return new ViewHolder(viewGroup);
        }

        @Override
        public void initData(RecyclerView.ViewHolder holder, final FileItem fileItem, int position) {
            final View view = holder.itemView;
            switch (holder.getItemViewType()) {
                case TYPE_DOC:
                    final CheckBox docCbChoose = (CheckBox) view.findViewById(R.id.cb_choose);
                    TextView docName = (TextView) view.findViewById(R.id.tv_name);
                    docCbChoose.setChecked(mSelectedData.contains(fileItem));
                    docName.setText(fileItem.getName());
                    break;
                case TYPE_FOLDER:
                    TextView folderName = (TextView) view.findViewById(R.id.tv_name);
                    folderName.setText(fileItem.getName());
                    break;
                case TYPE_DIVIDER:
                    break;
                case TYPE_EMPTYE:
                    break;
            }
            if (onClickListener != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(view, fileItem);
                    }
                });
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public void setData(List<FileItem> mData) {
            if (mData == null || mData.isEmpty()) {
                mData = new ArrayList<>();
                FileItem fileItem = new FileItem(TYPE_EMPTYE);
                mData.add(fileItem);
            }
            super.setData(mData);
        }

        public List<FileItem> getSelectedData() {
            return mSelectedData;
        }

        public void setDefaultData(List<FileItem> defaultData) {
            if (defaultData == null) {
                defaultData = new ArrayList<>();
            }
            this.mSelectedData = defaultData;
        }

        public OnClickListener getOnClickListener() {
            return onClickListener;
        }

        public void setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

    }
```

### Step 2. new an Activity extends FilePickerBaseActivity like this

```Java
    public class CustomActivity extends FilePickerBaseActivity {

        public static Intent getStartIntent(Context context, List<FileItem> selectedFiles, int colorResId) {
            Intent intent = new Intent(context, CustomActivity.class);
            intent.putExtra(EXTRA_SELECTED_FILES, (Serializable) selectedFiles);
            intent.putExtra(EXTRA_TITLE_COLOR, colorResId);
            return intent;
        }

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            toolbarColorResId = getIntent().getIntExtra(EXTRA_TITLE_COLOR, R.color.colorAccent);
        }

        @Override
        public BaseFileAdapter initAdapter() {
            // the customized adapter
            return new CommonFileAdapter(CustomActivity.this);
        }
    }
```

### Step 3. start

```Java
    startActivityForResult(
       CustomActivity.getStartIntent(MainActivity.this, files, R.color.colorPrimaryDark), 1005);
```