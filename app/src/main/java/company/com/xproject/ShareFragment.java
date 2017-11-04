package company.com.xproject;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhongchao on 2017/10/30.
 */

public class ShareFragment extends android.app.Fragment {
    @BindView(R.id.btn_share)
    Button mShareBtn;
    private ShareAction mShareAction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        ButterKnife.bind(this, view);

        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]{
                //SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE
                SHARE_MEDIA.QQ,
                SHARE_MEDIA.QZONE
        };
        UMImage image = new UMImage(this.getActivity(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        mShareAction = new ShareAction(ShareFragment.this.getActivity()).setDisplayList(displaylist)
                .withText(".....")
                .withMedia(image);//分享文本
                //.withFile()
                //.withMedia()
                //.....
        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始分享操作
                mShareAction.open();
            }
        });

        return view;
    }
}
