package company.com.xproject;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhongchao on 2017/10/30.
 */

public class SenderFragment extends android.app.Fragment {
    @BindView(R.id.edt_begin)
    TextInputEditText mBeginEdit;
    @BindView(R.id.edt_end)
    TextInputEditText mEndEdit;
    @BindView(R.id.btn_route)
    Button mRouteBtn;
    @BindView(R.id.mTexturemap)
    TextureMapView mMapView;

    private BaiduMap mBaiduMap;
    private Polyline mPolyline;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        SDKInitializer.initialize(this.getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_sender, container, false);
        ButterKnife.bind(this, view);

        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawLine();
            }
        });


        return view;
    }

    private void drawLine() {
        mBaiduMap.clear();
        String beginText = mBeginEdit.getText().toString();
        String endText = mEndEdit.getText().toString();

        String[] beginArr = beginText.split(",");
        String[] endArr = endText.split(",");

        double lon = Double.valueOf(beginArr[0]);
        double lat = Double.valueOf(beginArr[1]);
        LatLng p1 = new LatLng(lat, lon);

        lon = Double.valueOf(endArr[0]);
        lat = Double.valueOf(endArr[1]);
        LatLng p2 = new LatLng(lat, lon);

        List<LatLng> points = new ArrayList<>();
        points.add(p1);
        points.add(p2);

        OverlayOptions options = new PolylineOptions().width(10).color(0xAAFF0000).points(points);
        mPolyline = (Polyline) mBaiduMap.addOverlay(options);

        addMarker(p1, p2);

        jumpToLocation(p2);
    }

    private void addMarker(LatLng p1, LatLng p2) {
        //创建OverlayOptions的集合
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();

        //创建OverlayOptions属性
        BitmapDescriptor bitmap1 = BitmapDescriptorFactory.fromResource(R.mipmap.begin_marker);
        BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromResource(R.mipmap.end_marker);
        OverlayOptions option1 = new MarkerOptions()
                .position(p1)
                .icon(bitmap1);
        OverlayOptions option2 = new MarkerOptions()
                .position(p2)
                .icon(bitmap2);

        //将OverlayOptions添加到list
        options.add(option1);
        options.add(option2);
        //在地图上批量添加
        mBaiduMap.addOverlays(options);
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

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onPause();
    }
}
