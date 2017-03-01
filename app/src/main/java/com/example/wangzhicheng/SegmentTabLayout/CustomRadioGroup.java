package com.example.wangzhicheng.SegmentTabLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhicheng on 2017/1/4.
 */

public class CustomRadioGroup extends RadioGroup{
    private Context mContext;
    private int horizontalSpacing=20;//水平间距
    private int verticaSpacing=24;//垂直间距
    private OnClickListener listener;
    private List<RowView>rowViews;
    private boolean isExtended=false;

    private  ImageView imageView;
    private LinearLayout linearLayout;
    public CustomRadioGroup(Context context) {
        super(context,null);
    }

    public CustomRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        rowViews=new ArrayList<>();
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = dip2px(mContext,horizontalSpacing);
    }

    public void setVerticaSpacing(int verticaSpacing) {
        this.verticaSpacing = dip2px(mContext,verticaSpacing);
    }

    public int dip2px(Context context, int dip){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dip*scale+0.5f);
    }
    public void setChilds(final String[]strings){
        for (int i=0;i<strings.length;i++){
            RadioButton radioButton = (RadioButton) LayoutInflater.from(mContext).inflate(R.layout.card_radiobutton, null);
            radioButton.setText(strings[i]);
            radioButton.setTextSize(dip2px(mContext,3));
            final int j=i;
            radioButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, strings[j],Toast.LENGTH_SHORT).show();
                }
            });
            this.addView(radioButton);
        }
            imageView = new ImageView(mContext);
            imageView.setImageResource(R.mipmap.arrow_down);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setPadding(0,dip2px(mContext,10),0,dip2px(mContext,11));
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    isExtended = !isExtended;
                    requestLayout();
                }
            });
            this.addView(imageView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        rowViews.clear();
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int nopaddingWidth=width-getPaddingLeft()-getPaddingRight();
        RowView rowView=null;
        for (int i=0;i<getChildCount()-1;i++){
            View childView=getChildAt(i);
            childView.measure(0,0);
            if (rowView==null){
                rowView=new RowView();
            }
            if (rowView.getLineViews().size()==0){
                rowView.addChildView(childView);
            }else if(rowView.getRowWidth()+horizontalSpacing+childView.getMeasuredWidth()>nopaddingWidth){
                //换行
                rowViews.add(rowView);
                rowView=new RowView();
                rowView.addChildView(childView);
            }else{
                rowView.addChildView(childView);
            }if (i==getChildCount()-2){
                rowViews.add(rowView);
            }
        }
            int height=getPaddingTop()+getPaddingBottom();
            for(int i=0;i<rowViews.size();i++){
                height+=rowViews.get(i).getRowHeight();
            }
            if (rowViews.size()<=3){
                height+=(rowViews.size()-1)*verticaSpacing;
                setMeasuredDimension(width,height);
                return;
            }
            if (isExtended){
                height+=(rowViews.size()-1)*verticaSpacing+imageView.getHeight();
            }
            else{
            height=getPaddingTop()+getPaddingBottom();;
            for (int i=0;i<3;i++)
                if (rowViews.get(i)!=null){
                    height+=rowViews.get(i).getRowHeight();
                }
            height+=2*verticaSpacing+imageView.getHeight();
           }
        setMeasuredDimension(width,height);
        if (getChildCount()==0){
            setMeasuredDimension(0,0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int paddingLeft=getPaddingLeft();
        int paddingTop=getPaddingTop();
        for (int i=0;i<rowViews.size();i++){
            RowView rowView=rowViews.get(i);
            if (i>0){
                paddingTop+=rowViews.get(i-1).getRowHeight()+verticaSpacing;
            }
            if (i==3&&!isExtended)break;
            List<View>viewList=rowView.getLineViews();
            for (int j=0;j<viewList.size();j++){
                View childView=viewList.get(j);
                if (j==0){
                    childView.layout(paddingLeft,paddingTop,paddingLeft+childView.getMeasuredWidth(),paddingTop+childView.getMeasuredHeight());
                }else{
                    View preView=viewList.get(j-1);
                    int left=preView.getRight()+horizontalSpacing;
                    childView.layout(left,preView.getTop(),left+childView.getMeasuredWidth(),preView.getBottom());
                }
            }
        }
        if (rowViews.size()<=3){
            return;
        }
        if (isExtended){
            imageView.setImageResource(R.mipmap.arrow_up);
            paddingTop+=rowViews.get(rowViews.size()-1).getRowHeight()+verticaSpacing;}
        else{
            imageView.setImageResource(R.mipmap.arrow_down);
        }
        imageView.layout(getMeasuredWidth()/2-imageView.getMeasuredWidth()/2,paddingTop-verticaSpacing,getMeasuredWidth()/2+imageView.getMeasuredWidth( )/2,paddingTop+imageView.getHeight());
    }

    class RowView{
        private List<View>lineViews;
        private int rowWidth;
        private int rowHeight;

        public RowView() {
            this.lineViews = new ArrayList<>();
        }
        public List<View>getLineViews(){
            return lineViews;
        }

        public int getRowWidth() {
            return rowWidth;
        }

        public int getRowHeight() {
            return rowHeight;
        }
        public void addChildView(View view){
            if (lineViews.size()==0){
                rowWidth=view.getMeasuredWidth();
            }else{
                rowWidth+=view.getMeasuredWidth()+horizontalSpacing;
            }
            rowHeight=Math.max(view.getMeasuredHeight(),rowHeight);
            lineViews.add(view);
        }
    }
}
