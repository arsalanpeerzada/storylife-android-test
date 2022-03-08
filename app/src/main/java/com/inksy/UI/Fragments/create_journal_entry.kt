package com.inksy.UI.Fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.setPadding
import androidx.core.view.size
import androidx.databinding.adapters.AdapterViewBindingAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.burhanrashid52.photoediting.*
import com.burhanrashid52.photoediting.TextEditorDialogFragment.Companion.show
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.inksy.Interfaces.OnDialogBulletClickListener
import com.inksy.Interfaces.PopUpOnClickListerner
import com.inksy.Interfaces.iOnClickListerner
import com.inksy.Model.Styles
import com.inksy.R
import com.inksy.UI.Activities.MainActivity
import com.inksy.UI.Adapter.BulletAdapter
import com.inksy.UI.Adapter.BulletSelectAdapter
import com.inksy.UI.Constants
import com.inksy.UI.Dialogs.BulletDialog
import com.inksy.Utils.FileUtil
import com.inksy.Utils.TinyDB
import com.inksy.databinding.FragmentCreateJournalEntryBinding
import io.github.hyuwah.draggableviewlib.DraggableView
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.TextStyleBuilder
import ja.burhanrashid52.photoeditor.ViewType
import ja.burhanrashid52.photoeditor.shape.ShapeBuilder
import ja.burhanrashid52.photoeditor.shape.ShapeType
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException

class create_journal_entry : Fragment(), iOnClickListerner, OnPhotoEditorListener,
    PropertiesBSFragment.Properties, ShapeBSFragment.Properties, EmojiBSFragment.EmojiListener,
    StickerBSFragment.StickerListener,
    AdapterViewBindingAdapter.OnItemSelected, OnDialogBulletClickListener, PopUpOnClickListerner,
    View.OnFocusChangeListener, View.OnClickListener {

    companion object {
        private const val TYPE_BULLETS = 0
        private const val TYPE_CHECKBOX = 1
        private const val TYPE_NUMBERLIST = 2
        private const val TYPE_ALPHALIST = 3
    }

    lateinit var imageArray: JsonArray
    lateinit var textArray: JSONArray
    lateinit var bulletArray: JSONArray
    lateinit var llTestDraggable: DraggableView<LinearLayout>
    private var layoutDragActive = " "
    private var layoutcount: Int = 0
    var fragmentName = Constants.CREATEJOURNALENTRY
    private var mShapeBSFragment: ShapeBSFragment? = null
    private var mShapeBuilder: ShapeBuilder? = null
    private val PICK_IMAGE_BACKGROUND = 2
    lateinit var binding: FragmentCreateJournalEntryBinding
    private lateinit var cameraUri: Uri
    lateinit var array: Array<Int>

    lateinit var mPhotoEditor: PhotoEditor
    private val CAMERA_REQUEST = 52
    private val PICK_REQUEST = 53
    val PINCH_TEXT_SCALABLE_INTENT_KEY = "PINCH_TEXT_SCALABLE"

    lateinit var styles: Styles
    lateinit var bulletList: ArrayList<Styles>

    private var selectedFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateJournalEntryBinding.inflate(layoutInflater)

        styles = Styles()
        bulletList = ArrayList()

        init()
        initClickListener()
        array = arrayOf(
            R.color.applightblue,
            R.color.appBlueDark,
            R.color.grey,
            R.color.errorRed
        )

        var tinyDB: TinyDB? = TinyDB(requireContext())
        var jsonString = tinyDB?.getString("jsondata")

        if (jsonString != null && jsonString != "") {
            var jsonObject = JSONObject(jsonString)

            bulletArray = jsonObject.getJSONArray("ArrayofBullets")
            textArray = jsonObject.getJSONArray("ArrayofText")
            var gson = Gson()
            var index = bulletArray.length()
            var textarray = textArray.length()

            for (i in 0 until index) {
                var jsonObject = bulletArray.getJSONObject(i)
                var title = jsonObject.getString("title")
                var bulletList = jsonObject.get("bullet") as JSONArray
                var type = jsonObject.getInt("type")
                var axixX = jsonObject.getString("axixX")
                var axixY = jsonObject.getString("axixY")
                var layoutName = jsonObject.getString("layoutID")
                val titleModel: Styles = gson.fromJson(title, Styles::class.java)
                //var bullets = gson.fromJson(bulletList, Styles::class.java)

                var index = bulletList.length()

                var bullets: ArrayList<Styles> = ArrayList()
                for (j in 0 until index) {
                    var item = bulletList.getString(j)
                    val bullet: Styles = gson.fromJson(item, Styles::class.java)
                    bullets.add(bullet)
                }
                ondialogClick(
                    titleModel,
                    bullets,
                    type = type,
                    layoutName,
                    axixX.toFloat(),
                    axixY.toFloat(),
                )
            }
            for (k in 0 until textarray) {

                setTextArray(k)
            }
        }

        mShapeBSFragment = ShapeBSFragment()
        mShapeBSFragment!!.setPropertiesChangeListener(this)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setTextArray(k: Int) {
        val jsonObject = textArray.getJSONObject(k)
        val inputText: String = jsonObject.get("text") as String
        val colorCode: Int = jsonObject.get("colorcode") as Int
        val _typface: Int = jsonObject.get("typeface") as Int
        val isBold: Boolean = jsonObject.get("bold") as Boolean
        val isItalic: Boolean = jsonObject.get("italic") as Boolean
        val axixX: Double = jsonObject.get("axixX") as Double
        val axixY: Double = jsonObject.get("axixY") as Double


        val styleBuilder = TextStyleBuilder()
        styleBuilder.withTextColor(colorCode)
        styleBuilder.withTextSize(30f)



        if (_typface != 0) {
            val typeface = context?.resources?.getFont(_typface)
            styleBuilder.withTextFont(typeface!!)
        }

        if (isBold && isItalic) {
            styleBuilder.withTextStyle(Typeface.BOLD_ITALIC)

        } else if (isBold) {
            styleBuilder.withTextStyle(Typeface.BOLD)

        } else if (isItalic) {
            styleBuilder.withTextStyle(Typeface.ITALIC)
        }

        textArray.put(jsonObject)
        mPhotoEditor.addText(inputText, styleBuilder, axixX.toFloat(), axixY.toFloat())
    }

    private fun showBottomSheetDialogFragment(fragment: BottomSheetDialogFragment?) {
        if (fragment == null || fragment.isAdded) {
            return
        }
        fragment.show(childFragmentManager, fragment.tag)
    }

    fun init() {
        val pinchTextScalable =
            requireActivity().intent.getBooleanExtra(PINCH_TEXT_SCALABLE_INTENT_KEY, true)


        //Typeface mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");
        mPhotoEditor = PhotoEditor.Builder(requireContext(), binding.photoEditorView)
            .setPinchTextScalable(pinchTextScalable) // set flag to make text scalable when pinch
            //.setDefaultTextTypeface(mTextRobotoTf)
            //.setDefaultEmojiTypeface(mEmojiTypeFace)
            .build() // build photo editor sdk
        mPhotoEditor.setOnPhotoEditorListener(this)

        //Set Image Dynamically
        binding.photoEditorView.source!!.setImageResource(R.drawable.paris_tower)
    }

    private fun initClickListener() {
        binding.bullets.setOnClickListener {
            val array = arrayOf(
                R.drawable.bullets_list, R.drawable.checkbox,
                R.drawable.numbers_list, R.drawable.alphabetic
            )

            binding.itemList.adapter = BulletSelectAdapter(requireContext(), array, this)

            if (binding.itemList.visibility == View.VISIBLE) {
                binding.itemList.visibility = View.GONE
            } else {
                binding.itemList.visibility = View.VISIBLE
            }
        }
        binding.checked.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }

        binding.ivBack.setOnClickListener {
            val action =
                create_journal_entryDirections.actionCreateJournalEntryToCreateJournalIndex()
            findNavController().navigate(action)
        }

        binding.picture.setOnClickListener {
            //Add drawable sticker

            val intent2 = Intent()
            intent2.type = "image/*"
            intent2.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent2, "Select Picture"), PICK_REQUEST
            )
        }

        binding.backgroundPicture.setOnClickListener {
            val intent2 = Intent()
            intent2.type = "image/*"
            intent2.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent2, "Select Picture"), PICK_IMAGE_BACKGROUND
            )
        }

        binding.doodle.setOnClickListener {

            mPhotoEditor.setBrushDrawingMode(true)
            mShapeBuilder = ShapeBuilder()
            mPhotoEditor.setShape(mShapeBuilder)
            showBottomSheetDialogFragment(mShapeBSFragment)

        }

        binding.text.setOnClickListener {

            val textEditorDialogFragment = show(requireActivity() as AppCompatActivity)
            textEditorDialogFragment.setOnTextEditorListener(object :
                TextEditorDialogFragment.TextEditor {


                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDone(
                    inputText: String?,
                    colorCode: Int,
                    _typface: Int,
                    isBold: Boolean,
                    isItalic: Boolean
                ) {

                    val styleBuilder = TextStyleBuilder()
                    styleBuilder.withTextColor(colorCode)
                    styleBuilder.withTextSize(30f)


                    if (_typface != 0) {
                        val typeface = context?.resources?.getFont(_typface)
                        styleBuilder.withTextFont(typeface!!)
                    }

                    if (isBold && isItalic) {
                        styleBuilder.withTextStyle(Typeface.BOLD_ITALIC)
                    } else if (isBold) {
                        styleBuilder.withTextStyle(Typeface.BOLD)
                    } else if (isItalic) {
                        styleBuilder.withTextStyle(Typeface.ITALIC)
                    }


                    mPhotoEditor.addText(inputText, styleBuilder, 0f, 0f)
                }
            })
        }

        binding.buttonUndo.setOnClickListener {
            mPhotoEditor.undo()
        }

        binding.buttonRedo.setOnClickListener {
            mPhotoEditor.redo()
        }

        binding.plus.setOnClickListener {

            var layout = binding.texteditor
            var child = layout.childCount

            for (i in 0 until child) {
                var _data = layout.getChildAt(i) as? LinearLayout
                var myViewRect = Rect()
                //   data.getGlobalVisibleRect(myViewRect)

                var recyclerview = _data?.getChildAt(2) as RecyclerView

                var jsonString = bulletArray.toString()

                /// Toast.makeText(requireContext(), jsonString, Toast.LENGTH_SHORT).show()

                var tinyDB = TinyDB(requireContext())
                tinyDB.putString("jsondata", jsonString)

                val centreX = (_data?.x!! / 2).toInt()
                val centreY = (_data?.y!! / 2).toInt()

            }
            openPopUp("Edit", binding.plus, "Edit")
        }
    }

    private fun openPopUp(data: String, itemView: View, layoutType: String) {
        val contextWrapper = ContextThemeWrapper(requireContext(), R.style.popupMenuStyle)
        val popupMenu = PopupMenu(
            contextWrapper, itemView
        )
        popupMenu.setForceShowIcon(true)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.link -> {
                    val action =
                        CreateJournalIndexDirections.actionCreateJournalIndexToCreateJournalEntry()
                    findNavController().navigate(action)
                    return@OnMenuItemClickListener true
                }
                R.id.Edit -> {
                    var id: String = ""
                    if (layoutType == "item") {
                        val recyclerView = itemView.parent as RecyclerView
                        val layout = recyclerView.parent as LinearLayout
                        id = layout.tag.toString()

                        val textview = layout.getChildAt(1) as TextView

                        styles = Styles(textview.text.toString())


                    } else {
                        val textview = itemView as TextView
                        val layout = textview.parent as LinearLayout
                        id = layout.tag.toString()
                    }

                    openDialog(data.toInt(), true, id)

                    return@OnMenuItemClickListener true
                }
                else -> false
            }
        })
        popupMenu.inflate(R.menu.pop_up)
        popupMenu.show()

        if (data == "Edit" || data == "title") {
            popupMenu.menu.findItem(R.id.Edit).isVisible = false
        }

    }

    override fun onclick(position: Int) {
        super.onclick(position)

        openDialog(position, false, "")
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_REQUEST) {
                cameraUri = data!!.data!!

                val uri = data.data
                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().applicationContext.contentResolver,
                    uri
                )
                mPhotoEditor.addImage(bitmap)

            } else if (requestCode == PICK_IMAGE_BACKGROUND) {
                cameraUri = data!!.data!!

                val uri = data.data
                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().applicationContext.contentResolver,
                    uri
                )
                binding.photoEditorView.source.setImageBitmap(bitmap)


            } else if (requestCode == CAMERA_REQUEST) {
                val selectedFilePath: String = FileUtil.getPath(requireContext(), cameraUri)
                val file = File(selectedFilePath)
                val compressedImageFile: File? = null
                try {
                    cameraUri = Uri.fromFile(compressedImageFile)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                selectedFile = File(cameraUri.path!!)

            }
        }

    }


    override fun onEditTextChangeListener(rootView: View?, text: String?, colorCode: Int) {
        val textEditorDialogFragment =
            show(requireActivity() as AppCompatActivity, text!!, colorCode)
        textEditorDialogFragment.setOnTextEditorListener(object :
            TextEditorDialogFragment.TextEditor {

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDone(
                inputText: String?,
                colorCode: Int,
                _typface: Int,
                isBold: Boolean,
                isItalic: Boolean
            ) {

                val styleBuilder = TextStyleBuilder()
                styleBuilder.withTextColor(colorCode)
                styleBuilder.withTextSize(30f)

                if (_typface != 0) {
                    val typeface = context?.resources?.getFont(_typface)
                    styleBuilder.withTextFont(typeface!!)
                }
                if (isBold && isItalic) {
                    styleBuilder.withTextStyle(Typeface.BOLD_ITALIC)
                } else if (isBold) {
                    styleBuilder.withTextStyle(Typeface.BOLD)
                } else if (isItalic) {
                    styleBuilder.withTextStyle(Typeface.ITALIC)
                }
                mPhotoEditor.editText(rootView!!, inputText, styleBuilder)
            }
        })
    }

    override fun onAddViewListener(viewType: ViewType?, numberOfAddedViews: Int) {

    }

    override fun onRemoveViewListener(viewType: ViewType?, numberOfAddedViews: Int) {

    }

    override fun onStartViewChangeListener(viewType: ViewType?) {

    }

    override fun onStopViewChangeListener(viewType: ViewType?) {

    }

    override fun onTouchSourceImage(event: MotionEvent?) {

    }

    override fun onEmojiClick(emojiUnicode: String?) {

    }

    override fun onColorChanged(colorCode: Int) {

        if (colorCode == 1) {
            mPhotoEditor.brushEraser()
        } else {
            mPhotoEditor.setBrushDrawingMode(true)
            mPhotoEditor.setShape(mShapeBuilder!!.withShapeColor(colorCode))
        }

    }

    override fun onOpacityChanged(opacity: Int) {
        mPhotoEditor.setShape(mShapeBuilder!!.withShapeOpacity(opacity))
    }


    override fun onShapeSizeChanged(shapeSize: Int) {
        mPhotoEditor.setShape(mShapeBuilder!!.withShapeSize(shapeSize.toFloat()))
    }

    override fun onShapePicked(shapeType: ShapeType?) {
        mPhotoEditor.setShape(mShapeBuilder!!.withShapeType(shapeType))
    }


    override fun onStickerClick(bitmap: Bitmap?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onDialogClick(
        callBacktv: Styles,
        callbackrv: ArrayList<Styles>,
        type: Int,
        layoutid: String
    ) {
        //  var styles = callBacktv

        ondialogClick(callBacktv, callbackrv, type, layoutid, 0f, 0f)
    }

    private fun ondialogClick(
        callBacktv: Styles,
        callbackrv: ArrayList<Styles>,
        type: Int,
        layoutid: String,
        _axixX: Float,
        _axixY: Float
    ) {

        val linearLayout = LinearLayout(requireContext())
        linearLayout.setPadding(40)
        var linearlayoutparams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayout.layoutParams = linearlayoutparams
        linearLayout.orientation = LinearLayout.VERTICAL

        var x = _axixX
        var y = _axixY
        linearLayout.x = x
        linearLayout.y = y


        var rv = callbackrv
        if (callBacktv.data != "") {

            binding.texteditor.requestFocus()

            val view = View(requireContext())
            val textview = TextView(requireContext())
            textview.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textview.tag = "textview_$layoutcount"
            val finaldata = checkTextStyle(callBacktv)
            textview.text = Html.fromHtml(finaldata)
            textview.setTextColor(callBacktv.textColor!!)
            textview.textSize = callBacktv.fontsize?.toFloat()!!
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                textview.typeface = requireContext().resources.getFont(callBacktv.typeface!!)
            }

            view.layoutParams = LinearLayout.LayoutParams(
                20,
                20
            )
            if (callBacktv.textColor == 0) {
                textview.setTextColor(requireContext().resources.getColor(R.color.black))
            }

            linearLayout.addView(view)
            linearLayout.addView(textview)


            textview.setOnLongClickListener {
                //title edit
                openPopUp("title", textview, "title")
                true
            }


        }
        if (callbackrv.size > 0) {

            bulletList = callbackrv
            val recycler = RecyclerView(requireContext())
            recycler.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            recycler.layoutManager = LinearLayoutManager(requireContext())
            recycler.adapter = BulletAdapter(requireContext(), callbackrv, type, fragmentName, this)
            linearLayout.addView(recycler)

        }

        if (callBacktv.data != "" || callbackrv.size > 0) {
            var isEdit: Boolean = false

            for (i in 0 until binding.texteditor.childCount) {
                val layout = binding.texteditor.getChildAt(i) as LinearLayout
                if (layout.tag.toString() == layoutid) {
                    isEdit = true
                    binding.texteditor.removeViewAt(i)
                }
            }
            linearLayout.tag = "layoutCount_$layoutcount"
            linearLayout.setOnClickListener(this)
            binding.texteditor.addView(linearLayout)


            var axixX = linearLayout.x
            var axixY = linearLayout.y

            Toast.makeText(requireContext(), "Old Value : $_axixX : $_axixY ", Toast.LENGTH_LONG)
                .show()
            Toast.makeText(requireContext(), "New Value : $axixX : $axixY ", Toast.LENGTH_LONG)
                .show()

            var bulletArray = JSONArray()
            var bulletsJson = JSONObject()

            for (bullet in bulletList) {
                bulletArray.put(bullet)
            }
            bulletsJson.put("axixY", axixY)
            bulletsJson.put("axixX", axixX)
            bulletsJson.put("layoutID", linearLayout.tag)
            bulletsJson.put("type", type)
            bulletsJson.put("title", callBacktv)
            bulletsJson.put("bullet", bulletArray)

            if (!isEdit) {

                layoutcount++
            }
            this.bulletArray.put(bulletsJson)
            Log.d("data", this.bulletArray.toString())
        }
        binding.text.requestFocus()
    }

    private fun checkTextStyle(styles: Styles): String {
        var data = styles.data

        if (styles.isBold!!) {
            // holder.et.setTextAppearance(R.style.boldText)
            data = "<b>$data</b>"
        }
        if (styles.isItalic!!) {
            // holder.et.setTextAppearance(R.style.italicText)
            data = "<i>$data</i>"
        }
        if (styles.isunderline!!) {
            data = "<u>$data</u>"
        }
        if (styles.isstrike!!) {
            data = "<s>$data</s>"
        }

        return data!!
    }

    override fun popuponclick(data: String, itemView: View, list: ArrayList<Styles>) {
        //bullets Pop up
        bulletList = list
        openPopUp(data, itemView, "item")
    }

    private fun openDialog(position: Int, edit: Boolean, id: String) {

        var layoutid = id
        if (!edit) {
            styles = Styles()
            bulletList = ArrayList<Styles>()
            layoutid = ""

        }

        when (position) {
            TYPE_BULLETS -> {
                val checkBoxDiloag =
                    BulletDialog(
                        requireContext(), getString(R.string.Done), getString(R.string.cancel),
                        TYPE_BULLETS, this, fragmentName, styles, bulletList, layoutid
                    )
                checkBoxDiloag.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                checkBoxDiloag.show()
                binding.itemList.visibility = View.GONE
            }
            TYPE_CHECKBOX -> {
                val checkBoxDiloag =
                    BulletDialog(
                        requireContext(), getString(R.string.Done), getString(R.string.cancel),
                        TYPE_CHECKBOX, this, fragmentName, styles, bulletList, layoutid
                    )
                checkBoxDiloag.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                checkBoxDiloag.show()
                binding.itemList.visibility = View.GONE
            }
            TYPE_NUMBERLIST -> {
                val checkBoxDiloag =
                    BulletDialog(
                        requireContext(), getString(R.string.Done), getString(R.string.cancel),
                        TYPE_NUMBERLIST, this, fragmentName, styles, bulletList, layoutid
                    )
                checkBoxDiloag.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                checkBoxDiloag.show()
                binding.itemList.visibility = View.GONE
            }
            TYPE_ALPHALIST -> {
                val checkBoxDiloag =
                    BulletDialog(
                        requireContext(), getString(R.string.Done), getString(R.string.cancel),
                        TYPE_ALPHALIST, this, fragmentName, styles, bulletList, layoutid
                    )
                checkBoxDiloag.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                checkBoxDiloag.show()
                binding.itemList.visibility = View.GONE
            }

        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {

        Log.d("ViewCheck", v.toString())
    }

    override fun onClick(v: View?) {

        if (layoutDragActive == v?.tag) {
            val layout = v as LinearLayout

            llTestDraggable.disableDrag()
            layoutDragActive = ""
            var data = layout.background
            layout.setBackgroundResource(R.color.transparent)

        } else {
            val layout = v as LinearLayout
            layout.setBackgroundResource(R.drawable.border_layout_grey)

            layoutDragActive = v.tag.toString()


            llTestDraggable = DraggableView.Builder(layout)
                .setStickyMode(DraggableView.Mode.NON_STICKY)
                .build()
        }


        for (i in 0 until binding.texteditor.size) {

            val layout = binding.texteditor.getChildAt(i)

            if (layoutDragActive != layout.tag.toString()) {
                layout.setBackgroundResource(R.color.transparent)


            }
        }
    }

}