package com.example.sumnote.ui.Note

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sumnote.R
import com.example.sumnote.ui.Quiz.QuizRecyclerViewAdapter

//리사이클러뷰 어댑터 작성 => 재활용을 위해
class NoteRecyclerViewAdapter(
    val itemList : ArrayList<NoteItem>, //리사이클러뷰로 그려줄 노트들
    val onItemClickListener: OnItemClickListener // 클릭 리스너 => 사용자가 아이템 클릭시 화면 이동
//    val inflater : LayoutInflater //화면에 붙이기 위한 inflater
): RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHoler>(){ //리사이클러뷰 어댑터 상속받기 템플릿은 자기 자신

    private lateinit var inflater : LayoutInflater

    interface OnItemClickListener {
        fun onNoteItemClick(position: Int)
    }


    //생성자를 통해 받은 뷰를 부모 클래스로 넘겨주기
    //2. onCreateViewHolder에서 만든 뷰를 생성자로 전달받음
    //해당 위치에서 각 아이템에 대한 이벤트를 달 수 있음
    //data class NoteItem constructor(var id:Int, var title:String, var generatedDate:String)
    inner class ViewHoler(itemView: View): RecyclerView.ViewHolder(itemView){
        //노트 이미지
        val noteImage : ImageView
        //노트 값들
        val title : TextView
        val generatedDate : TextView
        //어댑터가 만들어지면 각 뷰의 값 초기화
        //3. init블럭 호출 => title과 generatedDate 텍스트 뷰가 세팅됨
        init {
            noteImage = itemView.findViewById(R.id.imgView_note)
            //생성한 노트 값들
            title = itemView.findViewById(R.id.txt_note_title)
            generatedDate = itemView.findViewById(R.id.txt_gen_date)

            //각 아이템 클릭에 대한 이벤트 달기
            //intent 활용하여 페이지 변환하는 부분 작성할 것
            itemView.setOnClickListener{
                val position: Int = absoluteAdapterPosition //아이템 위치 가져오기
                val titleText = itemList[position].title
//                val getnDate = itemList[position].generatedDate
                val getnDate = itemList[position].createdAt
                val id = itemList[position].noteId
                Log.d("noteList", "$titleText,$getnDate,$id")
                onItemClickListener.onNoteItemClick(position) //클릭 리스너로 현재 위치(아이템 아이디)를 보냄
            }
        }
    }

    //각 아이템을 그려줌

    //1. 호출되고 나면 아이템 하나가 들어갈 뷰를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        //각 뷰를 생성하는 부분
        //아이템에 해당하는 인플레이터 정의
        inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.note_list_item,parent,false)
        return ViewHoler(view) //뷰 홀더에 위에서 만든 뷰 넣어주기 => class ViewHolder의 생성자가 호출됨?
    }

    //리사이클러뷰에서 보여줄 아이템리스트의 사이즈
    override fun getItemCount(): Int {
        return itemList.size
    }

    //뷰를 그려주는 부분(바인딩 해준다.)
    // 4. 세팅해둔 텍스트뷰에 값 채워줌
    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        //홀더(위에서 생성한 홀더)에 값 할당
        holder.title.text = itemList[position].title
//        holder.generatedDate.text = itemList[position].generatedDate
        holder.generatedDate.text = itemList[position].createdAt

        //이미지는 position에 해당하는 값으로
        // 이미지의 리소스 ID 얻어오기
        val imageNumber = (position % 9) + 1 //모듈러연산 => img_note의 개수를 벗어나지 않도록
        val imageName = "img_note_$imageNumber"
        val resId = holder.itemView.context.resources.getIdentifier(imageName, "drawable", holder.itemView.context.packageName)
        holder.noteImage.setImageResource(resId)
    }
}