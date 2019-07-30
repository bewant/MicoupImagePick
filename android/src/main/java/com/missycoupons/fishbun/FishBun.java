package com.missycoupons.fishbun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.missycoupons.R;
import com.missycoupons.fishbun.define.Define;
import com.missycoupons.fishbun.ui.album.AlbumActivity;

import java.util.ArrayList;


public class FishBun {

    private static BaseProperty baseProperty;

    //    BaseProperty baseProperty;
    public static BaseProperty with(Activity activity) {
        return baseProperty = new BaseProperty(activity);
    }

    public static BaseProperty with(Fragment fragment) {
        return baseProperty = new BaseProperty(fragment);
    }

    public static class BaseProperty implements BasePropertyImpl {

        private String boardId;
        private String postNo;
        private String uploadUrl;
        private String cookie;
        private ArrayList<Uri> arrayPaths = new ArrayList<>();
        private Activity activity = null;
        private Fragment fragment = null;
        private int requestCode = Define.ALBUM_REQUEST_CODE;

        public BaseProperty(Activity activity) {
            this.activity = activity;
        }

        public BaseProperty(Fragment fragment) {
            this.fragment = fragment;
        }

        public BaseProperty setArrayPaths(ArrayList<Uri> arrayPaths) {
            this.arrayPaths = arrayPaths;
            return baseProperty;
        }

        public BaseProperty setAlbumThumbnailSize(int size) {
            Define.ALBUM_THUMBNAIL_SIZE = size;
            return baseProperty;
        }

        @Override
        public BaseProperty setPickerSpanCount(int spanCount) {

            if (spanCount <= 0)
                spanCount = 3;
            Define.PHOTO_SPAN_COUNT = spanCount;
            return baseProperty;

        }

        public BaseProperty setBoardId(String boardId) {
            this.boardId = boardId;
            return baseProperty;
        }

        public BaseProperty setPostNo(String postNo) {
            this.postNo = postNo;
            return baseProperty;
        }

        public BaseProperty setUploadUrl(String uploadUrl) {
            this.uploadUrl = uploadUrl;
            return baseProperty;
        }

        public BaseProperty setCookie(String cookie) {
            this.cookie = cookie;
            return baseProperty;
        }

        public BaseProperty setPickerCount(int count) {
            if (count <= 0)
                count = 1;
            Define.ALBUM_PICKER_COUNT = count;
            return baseProperty;
        }

        @Override
        public BaseProperty setActionBarColor(int actionbarColor) {
            Define.COLOR_ACTION_BAR = actionbarColor;
            return baseProperty;
        }

        @Override
        public BaseProperty setActionBarTitleColor(int actionbarTitleColor) {
            Define.COLOR_ACTION_BAR_TITLE_COLOR = actionbarTitleColor;
            return baseProperty;
        }

        @Override
        public BaseProperty setActionBarColor(int actionbarColor, int statusBarColor) {
            Define.COLOR_ACTION_BAR = actionbarColor;
            Define.COLOR_STATUS_BAR = statusBarColor;
            return baseProperty;
        }

        @Override
        public BaseProperty setCamera(boolean isCamera) {
            Define.IS_CAMERA = isCamera;
            return baseProperty;
        }

        @Override
        public BaseProperty setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return baseProperty;
        }

        @Override
        public BaseProperty textOnNothingSelected(String message) {
            Define.MESSAGE_NOTHING_SELECTED = message;
            return baseProperty;
        }

        @Override
        public BaseProperty textOnImagesSelectionLimitReached(String message) {
            Define.MESSAGE_LIMIT_REACHED = message;
            return baseProperty;
        }

        @Override
        public BaseProperty setButtonInAlbumActivity(boolean isButton) {
            Define.IS_BUTTON = isButton;
            return baseProperty;
        }

        @Override
        public BaseProperty setReachLimitAutomaticClose(boolean isAutomaticClose) {
            Define.IS_AUTOMATIC_CLOSE = isAutomaticClose;
            return baseProperty;
        }

        @Override
        public BaseProperty setAlbumSpanCount(int portraitSpanCount, int landscapeSpanCount) {
            Define.ALBUM_PORTRAIT_SPAN_COUNT = portraitSpanCount;
            Define.ALBUM_LANDSCAPE_SPAN_COUNT = landscapeSpanCount;
            return baseProperty;
        }

        @Override
        public BaseProperty setAlbumSpanCountOnlyLandscape(int landscapeSpanCount) {
            Define.ALBUM_LANDSCAPE_SPAN_COUNT = landscapeSpanCount;
            return baseProperty;
        }

        @Override
        public BaseProperty setAlbumSpanCountOnlPortrait(int portraitSpanCount) {
            Define.ALBUM_PORTRAIT_SPAN_COUNT = portraitSpanCount;
            return baseProperty;
        }

        @Override
        public BaseProperty setAllViewTitle(String allViewTitle) {
            Define.TITLE_ALBUM_ALL_VIEW = allViewTitle;
            return baseProperty;
        }

        @Override
        public BaseProperty setActionBarTitle(String actionBarTitle) {
            Define.TITLE_ACTIONBAR = actionBarTitle;
            return baseProperty;
        }

        public void startAlbum() {
            Context context = null;
            if (activity != null)
                context = activity;
            else if (fragment != null)
                context = fragment.getActivity();
            else
                try {
                    throw new Exception("Activity or Fragment Null");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            if (Define.ALBUM_THUMBNAIL_SIZE == -1)
                Define.ALBUM_THUMBNAIL_SIZE = (int) context.getResources().getDimension(R.dimen.album_thum_size);

            setDefaultMessage(context);

            Intent i = new Intent(context, AlbumActivity.class);
            i.putExtra("boardId", boardId);
            i.putExtra("postNo", postNo);
            i.putExtra("uploadUrl", uploadUrl);
            i.putExtra("cookie", cookie);
            i.putParcelableArrayListExtra(Define.INTENT_PATH, arrayPaths);

            if (activity != null)
                activity.startActivityForResult(i, requestCode);

            else if (fragment != null)
                fragment.startActivityForResult(i, requestCode);


        }


    }

    interface BasePropertyImpl {

        BaseProperty setArrayPaths(ArrayList<Uri> arrayPaths);

        BaseProperty setAlbumThumbnailSize(int size);

        BaseProperty setPickerSpanCount(int spanCount);

        BaseProperty setPickerCount(int count);

        BaseProperty setActionBarColor(int actionbarColor);

        BaseProperty setActionBarTitleColor(int actionbarTitleColor);

        BaseProperty setActionBarColor(int actionbarColor, int statusbarColor);

        BaseProperty setCamera(boolean isCamera);

        BaseProperty setRequestCode(int RequestCode);

        BaseProperty textOnNothingSelected(String message);

        BaseProperty textOnImagesSelectionLimitReached(String message);

        BaseProperty setButtonInAlbumActivity(boolean isButton);

        BaseProperty setReachLimitAutomaticClose(boolean isAutomaticClose);

        BaseProperty setAlbumSpanCount(int portraitSpanCount, int landscapeSpanCount);

        BaseProperty setAlbumSpanCountOnlyLandscape(int landscapeSpanCount);

        BaseProperty setAlbumSpanCountOnlPortrait(int portraitSpanCount);

        BaseProperty setAllViewTitle(String allViewTitle);

        BaseProperty setActionBarTitle(String actionBarTitle);

        void startAlbum();
    }


    private static void setDefaultMessage(Context context) {
        if (Define.MESSAGE_NOTHING_SELECTED.equals(""))
            Define.MESSAGE_NOTHING_SELECTED = context.getResources().getString(R.string.msg_no_selected);

        if (Define.MESSAGE_LIMIT_REACHED.equals(""))
            Define.MESSAGE_LIMIT_REACHED = context.getResources().getString(R.string.msg_full_image);

        if (Define.TITLE_ALBUM_ALL_VIEW.equals(""))
            Define.TITLE_ALBUM_ALL_VIEW = context.getResources().getString(R.string.str_all_view);

        if (Define.TITLE_ACTIONBAR.equals(""))
            Define.TITLE_ACTIONBAR = context.getResources().getString(R.string.album);
    }

}
