package company.com.xproject;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhongchao on 2017/10/30.
 */

public class LonLatFragment extends android.app.Fragment{
//    @BindView(R.id.mTexturemap)
//    TextureMapView mMapView = null;
    @BindView(R.id.edt_city)
    TextInputEditText mCityEdit;
    @BindView(R.id.edt_address)
    TextInputEditText mAddressEdit;
    @BindView(R.id.text_lon)
    TextView mLonText;
    @BindView(R.id.text_lat)
    TextView mLatText;
    @BindView(R.id.btn_search)
    Button mSearchBtn;

    private TextureMapView mMapView;

    private BaiduMap mBaiduMap;
    private GeoCoder mSearch;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(this.getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_lonlat, container, false);
        ButterKnife.bind(this, view);

//        alertDialog = new AlertDialog.Builder(LonLatFragment.this.getActivity()).create();
//        alertDialog.hide();
//        Window window = alertDialog.getWindow();
//        window.setContentView(R.layout.dialog_map);
//        mMapView = (TextureMapView) window.findViewById(R.id.mTexturemap);
//        mBaiduMap = mMapView.getMap();
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

//        mBaiduMap = mMapView.getMap();
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(listener);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出带地图的Dialog，让用户确认定位是否准确
                showMapDialog();



                mSearch.geocode(new GeoCodeOption()
                        .city(mCityEdit.getText().toString())
                        .address(mAddressEdit.getText().toString()));
            }
        });


        return view;
    }

    private void showMapDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(LonLatFragment.this.getActivity()).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_map);
        mMapView = (TextureMapView) window.findViewById(R.id.mTexturemap);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        Button okBtn = (Button) window.findViewById(R.id.btn_ok);
        Button cancelBtn = (Button) window.findViewById(R.id.btn_cancel);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSearch.destroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
//        mMapView.onResume();
//    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {

        public void onGetGeoCodeResult(GeoCodeResult result) {

            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有检索到结果
                System.out.println("Error:"+result.error);
                return;
            }

            LatLng latLng = result.getLocation();
            mLatText.setText(String.valueOf(latLng.latitude));
            mLonText.setText(String.valueOf(latLng.longitude));
            //获取地理编码结果
            setPointView(latLng);
            jumpToLocation(latLng);
        }

        @Override

        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
            }

            //获取反向地理编码结果
        }
    };


    private void setPointView(LatLng lng) {
        Bitmap markerIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.marker);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(markerIcon);
        OverlayOptions options = new MarkerOptions().position(lng).icon(bitmap);
        mBaiduMap.addOverlay(options);
    }

    private void jumpToLocation(LatLng point) {
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(point)
                .zoom(20)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }
}
