package rocks.poopjournal.todont.showcaseview

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.LinearLayout

class ShowcaseViewBuilder : View, OnTouchListener {
    private var mActivity: Activity? = null
    private var mTargetView: View? = null
    private val mCustomView: MutableList<View> = ArrayList()
    private val mCustomViewLeftMargins: MutableList<Float> = ArrayList()
    private val mCustomViewTopMargins: MutableList<Float> = ArrayList()
    private val mCustomViewRightMargins: MutableList<Float> = ArrayList()
    private val mCustomViewBottomMargins: MutableList<Float> = ArrayList()
    private val mCustomViewGravity: MutableList<Int> = ArrayList()
    private var mCenterX = 0f
    private var mCenterY = 0f
    private var mRadius = 0f
    private var mMarkerDrawable: Drawable? = null
    private var mMarkerDrawableGravity = 0
    private var ringColor = 0
    private var backgroundOverlayColor = 0
    private var mCustomViewMargin = 0
    private var mShape = SHAPE_CIRCLE
    private var mBgOverlayShape = FULL_SCREEN
    private var mRoundRectCorner = 0
    private val idsRectMap = HashMap<Rect, Int>()
    private val idsClickListenerMap = HashMap<Int, OnClickListener?>()
    private var mHideOnTouchOutside = false
    private var mRingWidth = 10f
    private var mShowcaseMargin = 12f
    private var mRoundRectOffset = 170f
    private var mMarkerDrawableLeftMargin = 0f
    private var mMarkerDrawableRightMargin = 0f
    private var mMarkerDrawableTopMargin = 0f
    private var mMarkerDrawableBottomMargin = 0f
    private var tempCanvas: Canvas? = null
    private var backgroundPaint: Paint? = null
    private var transparentPaint: Paint? = null
    private var ringPaint: Paint? = null
    private var mTargetViewGlobalRect: Rect? = null

    private constructor(context: Context) : super(context)

    private constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setTargetView(view: View?): ShowcaseViewBuilder {
        mTargetView = view
        return this
    }

    private fun calculateRadiusAndCenter() {
        val width = mTargetView!!.measuredWidth
        val height = mTargetView!!.measuredHeight

        val xy = intArrayOf(0, 0)
        mTargetView!!.getLocationInWindow(xy)

        mCenterX = (xy[0] + (width / 2)).toFloat()
        mCenterY = (xy[1] + (height / 2)).toFloat()

        mRadius = if (width > height) {
            (7 * (width) / 12).toFloat()
        } else {
            (7 * (height) / 12).toFloat()
        }
    }

    fun setHideOnTouchOutside(value: Boolean): ShowcaseViewBuilder {
        this.mHideOnTouchOutside = value
        return this
    }

    fun setMarkerDrawable(drawable: Drawable?, gravity: Int): ShowcaseViewBuilder {
        this.mMarkerDrawable = drawable
        this.mMarkerDrawableGravity = gravity
        return this
    }

    fun setDrawableLeftMargin(margin: Float): ShowcaseViewBuilder {
        this.mMarkerDrawableLeftMargin = margin
        return this
    }

    fun setRoundRectOffset(roundRectOffset: Float): ShowcaseViewBuilder {
        this.mRoundRectOffset = roundRectOffset
        return this
    }

    fun setDrawableRightMargin(margin: Float): ShowcaseViewBuilder {
        this.mMarkerDrawableRightMargin = margin
        return this
    }

    fun setDrawableTopMargin(margin: Float): ShowcaseViewBuilder {
        this.mMarkerDrawableTopMargin = margin
        return this
    }

    fun setDrawableBottomMargin(margin: Float): ShowcaseViewBuilder {
        this.mMarkerDrawableBottomMargin = margin
        return this
    }

    fun setShowcaseShape(shape: Int): ShowcaseViewBuilder {
        this.mShape = shape
        return this
    }

    fun setBgOverlayShape(bgOverlayShape: Int): ShowcaseViewBuilder {
        this.mBgOverlayShape = bgOverlayShape
        return this
    }

    fun setRoundRectCornerDirection(roundRectCornerDirection: Int): ShowcaseViewBuilder {
        this.mRoundRectCorner = roundRectCornerDirection
        return this
    }

    fun addCustomView(layoutId: Int, gravity: Int): ShowcaseViewBuilder {
        val view = LayoutInflater.from(mActivity).inflate(layoutId, null)
        val linearLayout = LinearLayout(mActivity)
        linearLayout.addView(view)
        linearLayout.gravity = Gravity.CENTER

        val metrics = DisplayMetrics()
        mActivity!!.windowManager.defaultDisplay.getMetrics(metrics)

        val rect = Rect()
        rect[0, 0, metrics.widthPixels] = metrics.heightPixels

        val widthSpec = MeasureSpec.makeMeasureSpec(rect.width(), MeasureSpec.EXACTLY)
        val heightSpec = MeasureSpec.makeMeasureSpec(rect.height(), MeasureSpec.EXACTLY)

        linearLayout.measure(widthSpec, heightSpec)
        mCustomView.add(linearLayout)
        mCustomViewGravity.add(gravity)
        mCustomViewLeftMargins.add(0f)
        mCustomViewTopMargins.add(0f)
        mCustomViewRightMargins.add(0f)
        mCustomViewBottomMargins.add(0f)
        return this
    }

    fun addCustomView(view: View?, gravity: Int): ShowcaseViewBuilder {
        val linearLayout = LinearLayout(mActivity)
        linearLayout.addView(view)
        linearLayout.gravity = Gravity.CENTER

        val metrics = DisplayMetrics()
        mActivity!!.windowManager.defaultDisplay.getMetrics(metrics)

        val rect = Rect()
        rect[0, 0, metrics.widthPixels] = metrics.heightPixels

        val widthSpec = MeasureSpec.makeMeasureSpec(rect.width(), MeasureSpec.EXACTLY)
        val heightSpec = MeasureSpec.makeMeasureSpec(rect.height(), MeasureSpec.EXACTLY)

        linearLayout.measure(widthSpec, heightSpec)
        mCustomView.add(linearLayout)
        mCustomViewGravity.add(gravity)
        mCustomViewLeftMargins.add(0f)
        mCustomViewTopMargins.add(0f)
        mCustomViewRightMargins.add(0f)
        mCustomViewBottomMargins.add(0f)
        return this
    }

    fun addCustomView(
        layoutId: Int,
        gravity: Int,
        leftMargin: Float,
        topMargin: Float,
        rightMargin: Float,
        bottomMargin: Float
    ): ShowcaseViewBuilder {
        val view = LayoutInflater.from(mActivity).inflate(layoutId, null)
        val linearLayout = LinearLayout(mActivity)
        linearLayout.addView(view)
        linearLayout.gravity = Gravity.CENTER

        val metrics = DisplayMetrics()
        mActivity!!.windowManager.defaultDisplay.getMetrics(metrics)

        val rect = Rect()
        rect[0, 0, metrics.widthPixels] = metrics.heightPixels

        val widthSpec = MeasureSpec.makeMeasureSpec(rect.width(), MeasureSpec.EXACTLY)
        val heightSpec = MeasureSpec.makeMeasureSpec(rect.height(), MeasureSpec.EXACTLY)

        linearLayout.measure(widthSpec, heightSpec)
        mCustomView.add(linearLayout)
        mCustomViewGravity.add(gravity)
        mCustomViewLeftMargins.add(leftMargin)
        mCustomViewTopMargins.add(topMargin)
        mCustomViewRightMargins.add(rightMargin)
        mCustomViewBottomMargins.add(bottomMargin)
        return this
    }

    fun addCustomView(
        view: View?,
        gravity: Int,
        leftMargin: Float,
        topMargin: Float,
        rightMargin: Float,
        bottomMargin: Float
    ): ShowcaseViewBuilder {
        val linearLayout = LinearLayout(mActivity)
        linearLayout.addView(view)
        linearLayout.gravity = Gravity.CENTER

        val metrics = DisplayMetrics()
        mActivity!!.windowManager.defaultDisplay.getMetrics(metrics)

        val rect = Rect()
        rect[0, 0, metrics.widthPixels] = metrics.heightPixels

        val widthSpec = MeasureSpec.makeMeasureSpec(rect.width(), MeasureSpec.EXACTLY)
        val heightSpec = MeasureSpec.makeMeasureSpec(rect.height(), MeasureSpec.EXACTLY)

        linearLayout.measure(widthSpec, heightSpec)
        mCustomView.add(linearLayout)
        mCustomViewGravity.add(gravity)
        mCustomViewLeftMargins.add(leftMargin)
        mCustomViewTopMargins.add(topMargin)
        mCustomViewRightMargins.add(rightMargin)
        mCustomViewBottomMargins.add(bottomMargin)
        return this
    }

    fun addCustomView(view: View): ShowcaseViewBuilder {
        val metrics = DisplayMetrics()
        mActivity!!.windowManager.defaultDisplay.getMetrics(metrics)

        val rect = Rect()
        rect[0, 0, metrics.widthPixels] = metrics.heightPixels

        val widthSpec = MeasureSpec.makeMeasureSpec(rect.width(), MeasureSpec.EXACTLY)
        val heightSpec = MeasureSpec.makeMeasureSpec(rect.height(), MeasureSpec.EXACTLY)

        view.measure(widthSpec, heightSpec)
        mCustomView.add(view)
        mCustomViewGravity.add(0)
        mCustomViewLeftMargins.add(0f)
        mCustomViewTopMargins.add(0f)
        mCustomViewRightMargins.add(0f)
        mCustomViewBottomMargins.add(0f)
        return this
    }

    fun addCustomView(layoutId: Int): ShowcaseViewBuilder {
        val view = LayoutInflater.from(mActivity).inflate(layoutId, null)

        val metrics = DisplayMetrics()
        mActivity!!.windowManager.defaultDisplay.getMetrics(metrics)

        val rect = Rect()
        rect[0, 0, metrics.widthPixels] = metrics.heightPixels

        val widthSpec = MeasureSpec.makeMeasureSpec(rect.width(), MeasureSpec.EXACTLY)
        val heightSpec = MeasureSpec.makeMeasureSpec(rect.height(), MeasureSpec.EXACTLY)

        view.measure(widthSpec, heightSpec)
        mCustomView.add(view)
        mCustomViewGravity.add(0)
        mCustomViewLeftMargins.add(0f)
        mCustomViewTopMargins.add(0f)
        mCustomViewRightMargins.add(0f)
        mCustomViewBottomMargins.add(0f)
        return this
    }

    /**
     * Deprecated. Use @addCustomView(int layoutId, int gravity, int leftMargin, int topMargin, int rightMargin, int bottomMargin) instead
     *
     * @param margin
     * @return
     */
    fun setCustomViewMargin(margin: Int): ShowcaseViewBuilder {
        this.mCustomViewMargin = margin
        return this
    }

    fun setRingColor(color: Int): ShowcaseViewBuilder {
        this.ringColor = color
        return this
    }

    fun setRingWidth(ringWidth: Float): ShowcaseViewBuilder {
        this.mRingWidth = ringWidth
        return this
    }

    fun setShowcaseMargin(showcaseMargin: Float): ShowcaseViewBuilder {
        this.mShowcaseMargin = showcaseMargin
        return this
    }

    fun setBackgroundOverlayColor(color: Int): ShowcaseViewBuilder {
        this.backgroundOverlayColor = color
        return this
    }

    fun show() {
        transparentPaint = Paint()
        ringPaint = Paint()
        backgroundPaint = Paint()
        if (mTargetView != null) {
            if (mTargetView!!.width == 0 || mTargetView!!.height == 0) {
                mTargetView!!.viewTreeObserver.addOnGlobalLayoutListener(object :
                    OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        invalidate()
                        addShowcaseView()
                        mTargetView!!.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    }
                })
            } else {
                invalidate()
                addShowcaseView()
            }
        }
        setOnTouchListener(this)
    }

    private fun addShowcaseView() {
        (mActivity!!.window.decorView as ViewGroup).addView(this)
    }

    fun hide() {
        mCustomView.clear()
        mCustomViewGravity.clear()
        mCustomViewLeftMargins.clear()
        mCustomViewRightMargins.clear()
        mCustomViewTopMargins.clear()
        mCustomViewBottomMargins.clear()
        idsClickListenerMap.clear()
        idsRectMap.clear()
        mHideOnTouchOutside = false
        (mActivity!!.window.decorView as ViewGroup).removeView(this)
    }

    override fun onDraw(canvas: Canvas) {
        if (mTargetView != null) {
            setShowcase(canvas)
            drawMarkerDrawable(canvas)
            addCustomView(canvas)
        }
        super.onDraw(canvas)
    }

    private fun drawMarkerDrawable(canvas: Canvas) {
        if (mMarkerDrawable != null) {
            when (mMarkerDrawableGravity) {
                Gravity.LEFT, Gravity.START -> mMarkerDrawable!!.setBounds(
                    (mCenterX + mMarkerDrawableLeftMargin - mRadius - mMarkerDrawable!!.minimumWidth - mRingWidth - 10).toInt(),
                    (mCenterY + mMarkerDrawableTopMargin - mMarkerDrawable!!.minimumHeight).toInt(),
                    (mCenterX + mMarkerDrawableLeftMargin - mRadius - mRingWidth - 10).toInt(),
                    (mCenterY + mMarkerDrawableTopMargin).toInt()
                )

                Gravity.TOP -> mMarkerDrawable!!.setBounds(
                    (mCenterX + mMarkerDrawableLeftMargin - mMarkerDrawable!!.minimumWidth).toInt(),
                    (mCenterY + mMarkerDrawableTopMargin - mRadius - mMarkerDrawable!!.minimumHeight - mRingWidth - 10).toInt(),
                    (mCenterX + mMarkerDrawableLeftMargin).toInt(),
                    (mCenterY + mMarkerDrawableTopMargin - mRadius - mRingWidth - 10).toInt()
                )

                Gravity.RIGHT, Gravity.END -> mMarkerDrawable!!.setBounds(
                    (mCenterX + mMarkerDrawableLeftMargin + mRadius + mRingWidth + 10).toInt(),
                    (mCenterY + mMarkerDrawableTopMargin - mMarkerDrawable!!.minimumHeight).toInt(),
                    (mCenterX + mMarkerDrawableLeftMargin + mRadius + mMarkerDrawable!!.minimumWidth + mRingWidth + 10).toInt(),
                    (mCenterY + mMarkerDrawableTopMargin).toInt()
                )

                Gravity.BOTTOM -> mMarkerDrawable!!.setBounds(
                    (mCenterX + mMarkerDrawableLeftMargin - mMarkerDrawable!!.minimumWidth).toInt(),
                    (mCenterY + mMarkerDrawableTopMargin + mRadius + mRingWidth + 10).toInt(),
                    (mCenterX + mMarkerDrawableLeftMargin).toInt(),
                    (mCenterY + mMarkerDrawableTopMargin + mRadius + mMarkerDrawable!!.minimumHeight + mRingWidth + 10).toInt()
                )
            }

            mMarkerDrawable!!.draw(canvas)
        } else {
            Log.d(TAG, "No marker drawable defined")
        }
    }

    private fun addCustomView(canvas: Canvas) {
        if (mCustomView.size != 0) {
            for (i in mCustomView.indices) {
                val cy = (mCustomView[i].measuredHeight / 2).toFloat()
                val cx = (mCustomView[i].measuredWidth / 2).toFloat()
                var diffY: Float
                var diffX: Float
                val marginTop = mCustomViewTopMargins[i]
                val marginLeft = mCustomViewLeftMargins[i]
                val marginRight = mCustomViewRightMargins[i]
                val marginBottom = mCustomViewBottomMargins[i]
                mTargetViewGlobalRect = Rect()
                mTargetView!!.getGlobalVisibleRect(mTargetViewGlobalRect)
                val view = mCustomView[i]
                when (mCustomViewGravity[i]) {
                    Gravity.START, Gravity.LEFT -> {
                        diffY = mCenterY - cy
                        diffX = mCenterX - cx
                        if (diffX < 0) {
                            view.layout(
                                0, 0, (mCenterX - view.measuredWidth - 2 * marginRight).toInt(),
                                (mCustomView[i].measuredHeight + 2 * (diffY + marginTop)).toInt()
                            )
                        } else {
                            view.layout(
                                diffX.toInt(),
                                0,
                                (view.measuredWidth - diffX - 2 * marginRight).toInt(),
                                (mCustomView[i].measuredHeight + 2 * (diffY + marginTop)).toInt()
                            )
                        }
                    }

                    Gravity.TOP -> {
                        diffY = mCenterY - cy - 2 * mTargetView!!.measuredHeight
                        view.layout(
                            (-marginLeft).toInt(), 0, (view.measuredWidth + marginLeft).toInt(),
                            (mCustomView[i].measuredHeight + 2 * (diffY + marginTop)).toInt()
                        )
                    }

                    Gravity.END, Gravity.RIGHT -> {
                        diffY = mCenterY - cy
                        view.layout(
                            -2 * mTargetViewGlobalRect!!.right,
                            0,
                            (view.measuredWidth + 4 * marginLeft).toInt(),
                            (mCustomView[i].measuredHeight + 2 * (diffY + marginTop)).toInt()
                        )
                    }

                    Gravity.BOTTOM -> {
                        diffY = mCenterY - cy + 2 * mTargetView!!.measuredHeight
                        view.layout(
                            (-marginLeft).toInt(), 0, (view.measuredWidth + marginLeft).toInt(),
                            (mCustomView[i].measuredHeight + 2 * (diffY + marginTop)).toInt()
                        )
                    }

                    else -> mCustomView[i].layout(
                        0,
                        0,
                        mCustomView[i].measuredWidth,
                        mCustomView[i].measuredHeight
                    )
                }
                mCustomView[i].draw(canvas)
            }
        } else {
            Log.d(TAG, "No Custom View defined")
        }
    }

    private fun getAllChildren(v: View): ArrayList<View> {
        if (v !is ViewGroup) {
            val viewArrayList = ArrayList<View>()
            viewArrayList.add(v)
            return viewArrayList
        }

        val result = ArrayList<View>()

        val viewGroup = v
        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
            val viewArrayList = ArrayList<View>()
            viewArrayList.add(v)
            viewArrayList.addAll(getAllChildren(child))

            result.addAll(viewArrayList)
        }
        return result
    }

    private fun setShowcase(canvas: Canvas) {
        calculateRadiusAndCenter()
        val bitmap = Bitmap.createBitmap(canvas.width, canvas.height, Bitmap.Config.ARGB_8888)
        tempCanvas = Canvas(bitmap)

        backgroundPaint!!.color = backgroundOverlayColor
        backgroundPaint!!.isAntiAlias = true

        transparentPaint!!.color = resources.getColor(android.R.color.transparent)
        transparentPaint!!.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
        transparentPaint!!.isAntiAlias = true

        ringPaint!!.color = ringColor
        ringPaint!!.setXfermode(PorterDuffXfermode(PorterDuff.Mode.ADD))
        ringPaint!!.isAntiAlias = true

        if (mBgOverlayShape == ROUND_RECT) {
            val oval = RectF()
            when (mRoundRectCorner) {
                BOTTOM_LEFT -> {
                    oval[-mRoundRectOffset, -tempCanvas!!.height.toFloat(), 2 * tempCanvas!!.width + mRoundRectOffset] =
                        tempCanvas!!.height.toFloat()
                    tempCanvas!!.drawArc(oval, 90f, 90f, true, backgroundPaint!!)
                }

                BOTTOM_RIGHT -> {
                    oval[-tempCanvas!!.width - mRoundRectOffset, -tempCanvas!!.height.toFloat(), tempCanvas!!.width + mRoundRectOffset] =
                        tempCanvas!!.height.toFloat()
                    tempCanvas!!.drawArc(oval, 360f, 90f, true, backgroundPaint!!)
                }

                TOP_LEFT -> {
                    oval[-mRoundRectOffset, 0f, 2 * tempCanvas!!.width + mRoundRectOffset] =
                        (2 * tempCanvas!!.height).toFloat()
                    tempCanvas!!.drawArc(oval, 180f, 90f, true, backgroundPaint!!)
                }

                TOP_RIGHT -> {
                    oval[-tempCanvas!!.width - mRoundRectOffset, 0f, tempCanvas!!.width + mRoundRectOffset] =
                        (2 * tempCanvas!!.height).toFloat()
                    tempCanvas!!.drawArc(oval, 270f, 90f, true, backgroundPaint!!)
                }

                else -> {
                    oval[-mRoundRectOffset, -tempCanvas!!.height.toFloat(), 2 * tempCanvas!!.width + mRoundRectOffset] =
                        tempCanvas!!.height.toFloat()
                    tempCanvas!!.drawArc(oval, 90f, 90f, true, backgroundPaint!!)
                }
            }
        } else {
            tempCanvas!!.drawRect(
                0f, 0f, tempCanvas!!.width.toFloat(), tempCanvas!!.height.toFloat(),
                backgroundPaint!!
            )
        }

        if (mShape == SHAPE_SKEW) {
            val r = Rect()
            val ring = Rect()
            mTargetView!!.getGlobalVisibleRect(r)
            mTargetView!!.getGlobalVisibleRect(ring)
            //Showcase rect
            r.top = (r.top - mShowcaseMargin).toInt()
            r.left = (r.left - mShowcaseMargin).toInt()
            r.right = (r.right + mShowcaseMargin).toInt()
            r.bottom = (r.bottom + mShowcaseMargin).toInt()
            //Showcase ring rect
            ring.top = (ring.top - (mShowcaseMargin + mRingWidth)).toInt()
            ring.left = (ring.left - (mShowcaseMargin + mRingWidth)).toInt()
            ring.right = (ring.right + (mShowcaseMargin + mRingWidth)).toInt()
            ring.bottom = (ring.bottom + (mShowcaseMargin + mRingWidth)).toInt()
            tempCanvas!!.drawRect(ring, ringPaint!!)
            tempCanvas!!.drawRect(r, transparentPaint!!)
        } else {
            tempCanvas!!.drawCircle(mCenterX, mCenterY, mRadius + mRingWidth, ringPaint!!)
            tempCanvas!!.drawCircle(mCenterX, mCenterY, mRadius, transparentPaint!!)
        }

        canvas.drawBitmap(bitmap, 0f, 0f, Paint())
    }

    fun setClickListenerOnView(id: Int, clickListener: OnClickListener?) {
        idsClickListenerMap[id] = clickListener
    }

    private fun getAbsoluteLeft(myView: View?): Int {
        if (myView == null) {
            return 0
        }
        return if (myView.parent === myView.rootView) myView.left
        else myView.left + getAbsoluteLeft(myView.parent as View)
    }

    private fun getAbsoluteTop(myView: View?): Int {
        if (myView == null) {
            return 0
        }
        return if (myView.parent === myView.rootView) myView.top
        else myView.top + getAbsoluteTop(myView.parent as View)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (idsRectMap.isEmpty()) {
            for (parentView in mCustomView) {
                val childrenViews: List<View> = getAllChildren(parentView)
                for (view in childrenViews) {
                    val rect = Rect()
                    rect[getAbsoluteLeft(view), getAbsoluteTop(view), getAbsoluteLeft(view) + view.measuredWidth] =
                        getAbsoluteTop(view) + view.measuredHeight
                    if (view.id > 0) {
                        idsRectMap[rect] = view.id
                    }
                }
            }
        }

        if (event.action == MotionEvent.ACTION_UP) {
            val X = event.x
            val Y = event.y
            val keys: Array<Any> = idsRectMap.keys.toTypedArray()
            for (i in 0 until idsRectMap.size) {
                val r = keys[i] as Rect
                if (r.contains(X.toInt(), Y.toInt())) {
                    val id = idsRectMap[r]!!
                    if (idsClickListenerMap[id] != null) {
                        idsClickListenerMap[id]!!.onClick(v)
                        return true
                    }
                }
            }

            if (mHideOnTouchOutside) {
                hide()
                return true
            }
        }
        return false
    }

    companion object {
        private const val TAG = "SHOWCASE_VIEW"

        //Showcase Shapes constants
        const val SHAPE_CIRCLE: Int = 0 //Default Shape
        const val SHAPE_SKEW: Int = 1

        //Bg Overlay Shapes constants
        const val FULL_SCREEN: Int = 2 //Default Shape
        const val ROUND_RECT: Int = 3

        //Round rect corner direction constants
        const val BOTTOM_LEFT: Int = 4
        const val BOTTOM_RIGHT: Int = 5
        const val TOP_LEFT: Int = 6
        const val TOP_RIGHT: Int = 7

        @JvmStatic
        fun init(activity: Activity): ShowcaseViewBuilder {
            val showcaseViewBuilder = ShowcaseViewBuilder(activity)
            showcaseViewBuilder.mActivity = activity
            showcaseViewBuilder.isClickable = true
            return showcaseViewBuilder
        }
    }
}

