# 신예리의 개발전기 Develography

> 커뮤니티 앱 프로젝트

![develography_cover](https://user-images.githubusercontent.com/68595933/192332560-a99407ab-1ac5-4e55-a3ca-c01edcf795cc.PNG)

## 시작

- 블로그를 활용한 커뮤니티 앱 프로젝트입니다.

---

## 개발

### 기간

- 22.08.02.~22.09.21.

### 목표

- 코틀린으로 주요 기능을 구현해 봅니다.
- Jetpack과 Firebase를 사용해 봅니다.

### 사용

- Kotlin
- Jetpack
- Firebase

---

## 기능

### 1. 인증(Firebase Auth)

![auth](https://user-images.githubusercontent.com/68595933/192332941-1b4bc082-be0e-4f18-babd-97f2d810662d.PNG)

- 1.1 로그인/로그아웃
  - 로그인 버튼을 클릭하면 유효성 검사 후 로그인합니다.
  - 비회원로그인 버튼을 클릭하면 회원가입 없이 임의로 로그인이 가능하나, 한 번 로그아웃하면 같은 계정으로 재로그인할 수 없습니다.
  - 로그아웃 버튼을 클릭하면 로그아웃 후 IntroActivity로 이동합니다.

```kotlin
// FBAuth.kt

        // 현재 사용자 uid 받아옴
        fun getUid(): String {

            // .getInstance() -> 데이터베이스에 접근할 수 있는 진입점
            auth = FirebaseAuth.getInstance()

            // 현재 사용자 uid를 문자열로 반환
            return auth.currentUser?.uid.toString()

        }

        // 현재 시간 받아옴
        fun getTime(): String {

            // 캘린더 인스턴스 생성
            val currentDateTime = Calendar.getInstance().time

            // SimpleDateFormat() -> 사용자가 임의로 표기 형식 지정 가능
            // Locale.KOREA -> 지역설정 한국
            val dateFormat = SimpleDateFormat("yy-MM-dd HH:mm", Locale.KOREA).format(currentDateTime)

            // 원하는 포맷 및 한국어로 시간 반환
            return dateFormat

        }
```
```kotlin
// LoginActivity.kt

        // 로그인 버튼 클릭하면
        binding.loginBtn.setOnClickListener {

            // 로그인 조건 확인
            var emailCheck = false
            var pwCheck = false

            // 이메일, 비밀번호
            val emailTxt = binding.email.text.toString()
            val pwTxt = binding.pw.text.toString()

            // 이메일 정규식
            val emailPattern = Patterns.EMAIL_ADDRESS

            // 이메일 검사
            if(emailTxt.isEmpty()) {
                emailCheck = false
                binding.emailArea.error = "이메일주소를 입력하세요"
            } else if(emailPattern.matcher(emailTxt).matches()) {
                emailCheck = true
                binding.emailArea.error = null
            } else {
                emailCheck = false
                binding.emailArea.error = "올바른 이메일이 아닙니다"
            }

            // 비밀번호 검사
            if(pwTxt.isEmpty()) {
                pwCheck = false
                binding.pwArea.error = "비밀번호를 입력하세요"
            } else if (pwTxt.length<6) {
                pwCheck = false
                binding.pwArea.error = "최소 6자 이상 입력하세요"
            } else if (pwTxt.length>20) {
                pwCheck = false
                binding.pwArea.error = "20자 이하로 입력하세요"
            } else {
                pwCheck = true
                binding.pwArea.error = null
            }

            // 로그인 조건을 모두 만족하면
            if(emailCheck and pwCheck) {

                // 로그인
                auth.signInWithEmailAndPassword(emailTxt, pwTxt)
                    .addOnCompleteListener(this) { task ->

                        // 성공하면
                        if (task.isSuccessful) {

                            // 명시적 인텐트 -> 다른 액티비티 호출
                            val intent = Intent(this, MainActivity::class.java)

                            // 메인 액티비티 시작
                            startActivity(intent)

                            // 로그인 액티비티 종료
                            finish()

                        // 실패하면
                        } else {

                            // 메시지 띄움
                            Toast.makeText(this, "이메일과 비밀번호를 다시 확인하세요", Toast.LENGTH_LONG).show()

                        }

                    }

            // 조건 불만족하면
            } else {

                // 메시지 띄움
                Toast.makeText(this, "이메일과 비밀번호를 다시 확인하세요", Toast.LENGTH_LONG).show()

            }

        }
```
```kotlin
// IntroActivity.kt

        // 비회원 버튼 클릭하면
        binding.guestBtn.setOnClickListener {

            // 익명으로 로그인
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->

                    // 성공하면
                    if (task.isSuccessful) {

                        // 메인 액티비티 시작
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        // 인트로 액티비티 종료
                        finish()

                    // 실패하면
                    } else {

                        // 토스트 메시지 띄움
                        Toast.makeText(this, "비회원 로그인에 실패했습니다", Toast.LENGTH_LONG).show()

                    }

                }

        }
```
```kotlin
// MainActivity.kt

        // 로그아웃 버튼
        alertDialog.findViewById<ConstraintLayout>(R.id.logout)?.setOnClickListener {

            // 로그아웃 실행
            Firebase.auth.signOut()

            // 로그아웃 확인 메시지지
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(this, IntroActivity::class.java)

            // 인트로 액티비티 시작
            startActivity(intent)

            // 메인 액티비티 종료
            finish()

        }
```

- 1.2 회원가입
  - 회원가입 버튼을 클릭하면 유효성 검사 후 회원가입합니다.

```kotlin
// JoinActivity.kt

        // 회원가입 버튼 클릭하면
        binding.joinBtn.setOnClickListener {

            // 회원가입 조건 확인
            var emailCheck = false
            var pwCheck = false
            var pw2Check = false

            // 이메일, 비밀번호, 비밀번호 확인
            val emailTxt = binding.email.text.toString()
            val pwTxt = binding.pw.text.toString()
            val pw2Txt = binding.pw2.text.toString()

            // 이메일 정규식
            val emailPattern = Patterns.EMAIL_ADDRESS

            // 이메일 검사
            if(emailTxt.isEmpty()) {
                emailCheck = false
                binding.emailArea.error = "이메일주소를 입력하세요"
            } else if(emailPattern.matcher(emailTxt).matches()) {
                emailCheck = true
                binding.emailArea.error = null
            } else {
                emailCheck = false
                binding.emailArea.error = "올바른 이메일이 아닙니다"
            }

            // 비밀번호 검사
            if(pwTxt.isEmpty()) {
                pwCheck = false
                binding.pwArea.error = "비밀번호를 입력하세요"
            } else if (pwTxt.length<6) {
                pwCheck = false
                binding.pwArea.error = "최소 6자 이상 입력하세요"
            } else if (pwTxt.length>20) {
                pwCheck = false
                binding.pwArea.error = "20자 이하로 입력하세요"
            } else {
                pwCheck = true
                binding.pwArea.error = null
            }

            // 공란 검사
            if(emailTxt.isEmpty() || pwTxt.isEmpty() || pw2Txt.isEmpty()) {
                emailCheck = false
                pwCheck = false
                pw2Check = false
            }

            // 비밀번호 일치 검사
            if(pwTxt == pw2Txt && pwTxt.isNotEmpty() && pw2Txt.isNotEmpty()) {
                pw2Check = true
                binding.pw2Area.error = null
            } else if(pwTxt == pw2Txt && pwTxt.isEmpty() && pw2Txt.isEmpty()) {
                pw2Check = false
                binding.pw2Area.error = "비밀번호를 한 번 더 입력하세요"
            } else {
                pw2Check = false
                binding.pw2Area.error = "비밀번호가 일치하지 않습니다"
            }

            // 회원가입 조건을 모두 만족하면
            if (emailCheck and pwCheck and pw2Check) {

                // 회원가입
                auth.createUserWithEmailAndPassword(emailTxt, pwTxt)
                    .addOnCompleteListener(this) { task ->

                        // 성공하면
                        if (task.isSuccessful) {

                            // 명시적 인텐트 -> 다른 액티비티 호출
                            val intent = Intent(this, MainActivity::class.java)

                            // 메인 액티비티 시작
                            startActivity(intent)

                            // 조인 액티비티 종료
                            finish()

                        // 조건 만족해도
                        } else {

                            // 가입 불가능한 경우가 있음
                            Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_LONG).show()

                        }

                    }

            // 조건을 만족하지 못하면
            } else {

                // 가입 불가
                Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_LONG).show()

            }

        }
```

### 2. 웹뷰(Web View)

![webview](https://user-images.githubusercontent.com/68595933/192333063-c18a7ff9-32bf-449b-bdfe-225eb456ec2a.PNG)

- 2.1 블로그 탭
  - 컨텐츠 등록시 Glide로 썸네일을 지정합니다.
  - 컨텐츠 왼쪽 하단 하트 아이콘을 클릭하면 북마크 추가/삭제를 할 수 있습니다.
  - 컨텐츠를 카테고리별로 분류해 웹뷰로 보여주며, 뒤로가기 버튼을 두 번 클릭하면 웹뷰 창을 닫습니다.

- 2.2 깃허브 탭
  - shinyelee 계정의 깃허브 홈페이지를 보여주며, 화면 하단 내비게이션 바를 통해 다른 탭으로 이동합니다.

```kotlin
// FBRef.kt

        // 블로그 카테고리
        val androidStudio = database.getReference("android_studio")
        val kotlinSyntax = database.getReference("kotlin_syntax")
        val errorWarning = database.getReference("error_warning")
        val vcsGithub = database.getReference("vcs_github")
        val webInternet = database.getReference("web_internet")

        // 북마크 목록
        val bookmarkRef = database.getReference("bookmark_list")

        // 게시글
        val boardRef = database.getReference("board")

        // 댓글
        val commentRef = database.getReference("comment")
```
```kotlin
// ContentsModel.kt

// 블로그 컨텐츠에 대한 정보를 데이터 모델 형태로 묶음
data class ContentsModel (

    // 블로그 컨텐츠의 제목
    var title : String = "",

    // 블로그 컨텐츠의 썸네일 이미지 url
    var imageUrl : String = "",

    // 블로그 컨텐츠의 본문 url
    var webUrl : String = ""

)
```
```kotlin
// ContentsRVAdapter.kt

    // 뷰홀더 객체 생성 및 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentsRVAdapter.Viewholder {

        // 레이아웃 인플레이터 -> 리사이클러뷰에서 뷰홀더 만들 때 반복적으로 사용
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent, false)

        // 아직 데이터는 들어가있지 않은 껍데기
        return Viewholder(v)

    }

    // 뷰홀더 객체와 데이터를 연결
    override fun onBindViewHolder(holder: ContentsRVAdapter.Viewholder, position: Int) {

        // 껍데기(뷰홀더의 레이아웃)에 출력할 내용물(아이템 목록, 아이템의 키 목록)을 넣어줌
        holder.bindItems(items[position], keyList[position])

    }

    // 아이템들의 총 개수 반환
    override fun getItemCount(): Int = items.size

    // 각 아이템에 데이터 넣어줌
    inner class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        // 데이터 매핑(아이템, 아이템의 키)
        fun bindItems(item: ContentsModel, key: String) {

            // 리사이클러뷰는 setOnItemClickListener 없음 -> 개발자가 직접 구현해야 함

            // 아이템뷰(아이템 영역)를 클릭하면
            itemView.setOnClickListener {

                // 명시적 인텐트 -> 다른 액티비티 호출
                val intent = Intent(context, ContentsShowActivity::class.java)

                // 해당 아이템의 본문 url을 전달
                intent.putExtra("url", item.webUrl)

                // 컨텐츠쇼 액티비티 시작(웹뷰)
                itemView.context.startActivity(intent)

            }

            // 아이템의 제목 -> titleArea에 넣음
            contentsTitle.text = item.title

            // 아이템의 썸네일 -> 글라이드로 썸네일 이미지의 url을 imageViewArea에 넣음
            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }

    }
```
```kotlin
// ContentsListActivity.kt

        // 컨텐츠모델 형식의 아이템(=컨텐츠=제목+썸네일+본문) 목록
        val items = ArrayList<ContentsModel>()

        // 각 아이템의 키(=아이디) 목록 -> 북마크에 필요
        val keyList = ArrayList<String>()

        // 리사이클러뷰 어댑터 연결(컨텍스트, 아이템 목록, 키 목록, 북마크 아이디 목록)
        rvAdapter = ContentsRVAdapter(baseContext, items, keyList, bookmarkIdList)

        // 파이어베이스
        val database = Firebase.database

        // 카테고리
        val category = intent.getStringExtra("category")

        // .getReference() -> 데이터베이스의 루트 폴더 주소 값을 반환
        // 카테고리에 해당하는 데이터를 파이어베이스에서 가져옴
        when (category) {
            "android_studio" -> {
                myRef = database.getReference("android_studio")
            }
            "kotlin_syntax" -> {
                myRef = database.getReference("kotlin_syntax")
            }
            "error_warning" -> {
                myRef = database.getReference("error_warning")
            }
            "vcs_github" -> {
                myRef = database.getReference("vcs_github")
            }
            "web_internet" -> {
                myRef = database.getReference("web_internet")
            }
        }

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 데이터 스냅샷 내 데이터모델 형식으로 저장된
                for(dataModel in dataSnapshot.children) {

                    // 아이템을 받아
                    val item = dataModel.getValue(ContentsModel::class.java)

                    // 아이템 목록에 넣음
                    items.add(item!!)

                    // 키 값은 아이템 키 목록에 넣음
                    keyList.add(dataModel.key.toString())

                }

                // 동기화(새로고침) -> 리스트 크기 및 아이템 변화를 어댑터에 알림
                rvAdapter.notifyDataSetChanged()

            }

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터의 변화(추가)를 알려줌
        myRef.addValueEventListener(postListener)

        // 리사이클러뷰 어댑터 연결
        val rv : RecyclerView = binding.rv
        rv.adapter = rvAdapter

        // 그리드 레이아웃 매니저 -> 아이템을 격자 형태로 배치(2열)
        rv.layoutManager = GridLayoutManager(this, 2)

        // 북마크 정보를 가져옴
        getBookmarkData()

        // 뒤로가기 버튼 -> 컨텐츠리스트 액티비티 종료
        binding.backBtn.setOnClickListener {
            finish()
        }
```
```kotlin
// ContentsShowActivity.kt

        // 해당 아이템(컨텐츠) 본문의 url을 얻어와서
        val getUrl = intent.getStringExtra("url")

        // 웹뷰에 넣음
        binding.webView.loadUrl(getUrl.toString())
```
```kotlin
// BlogFragment.kt

        // 안드로이드 아이콘 클릭하면
        binding.androidIcon.setOnClickListener {

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(context, ContentsListActivity::class.java)

            // android_studio 카테고리로 데이터 넘겨줌
            intent.putExtra("category", "android_studio")

            // 컨텐츠리스트 액티비티 시작
            startActivity(intent)

        }

        // 코틀린 아이콘 -> kotlin_syntax
        binding.kotlinIcon.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("category", "kotlin_syntax")
            startActivity(intent)
        }

        // 생략
```
```kotlin
// WebFragment.kt

        // 웹뷰에 깃허브 링크 넣음
        binding.webView.loadUrl("https://github.com/shinyelee")
```

### 3. 게시판(CRUD)

![board_write](https://user-images.githubusercontent.com/68595933/192333230-07034857-d16b-4eb3-9ba7-42b56f2bd293.PNG)

- 3.1 게시글 CRUD
  - 게시글 작성시 Glide로 이미지를 추가합니다.
  - 게시글 쓰기/읽기/수정/삭제가 가능합니다.
  - 내가 쓴 게시글만 수정/삭제 버튼을 나타냅니다.

```kotlin
// BoardModel.kt

// 게시글에 대한 정보를 데이터 모델 형태로 묶음
data class BoardModel (

    // 게시글 제목
    var title : String = "",

    // 게시글 본문
    var main : String = "",

    // 작성자 uid
    var uid : String = "",

    // 작성 시간
    var time : String = ""

)
```
```kotlin
// BoardFragment.kt

// 리스트뷰 어댑터 연결(게시글 목록)
        boardLVAdapter = BoardLVAdapter(boardList)

        // 리스트뷰 어댑터 연결
        val lv : ListView = binding.boardLV
        lv.adapter = boardLVAdapter

        // 모든 게시글 정보를 가져옴
        getBoardListData()

        // 파이어베이스의 게시글 키를 기반으로 게시글 데이터(=제목+본문+uid+시간) 받아옴
        lv.setOnItemClickListener { parent, view, position, id ->

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(context, BoardReadActivity::class.java)

            // 글읽기 액티비티로 게시글의 키 값 전달
            intent.putExtra("key", boardKeyList[position])

            // 글읽기 액티비티 시작
            startActivity(intent)

        }

    // 모든 게시글 정보를 가져옴
    private fun getBoardListData() {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 게시글 목록 비움
                // -> 저장/삭제 마다 데이터 누적돼 게시글 중복으로 저장되는 것 방지
                boardList.clear()

                // 데이터 스냅샷 내 데이터모델 형식으로 저장된
                for(dataModel in dataSnapshot.children) {

                    // 로그
                    Log.d(TAG, "getBoardListData $dataModel")

                    // 아이템(=게시글)
                    val item = dataModel.getValue(BoardModel::class.java)

                    // 게시글 목록에 아이템 넣음
                    boardList.add(item!!)

                    // 게시글 키 목록에 문자열 형식으로 변환한 키 넣음
                    boardKeyList.add(dataModel.key.toString())

                }
                // getPostData()와 달리 반복문임 -> 아이템'들'

                // 게시글 키 목록을 역순으로 출력
                boardKeyList.reverse()
                Log.d(TAG, "getBoardListData - boardKeyList $boardKeyList")

                // 게시글 목록도 역순 출력
                boardList.reverse()

                // 동기화(새로고침) -> 리스트 크기 및 아이템 변화를 어댑터에 알림
                boardLVAdapter.notifyDataSetChanged()

            }

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터의 변화(추가)를 알려줌
        FBRef.boardRef.addValueEventListener(postListener)

    }
```
```kotlin
// BoardLVAdapter.kt

// boardList -> 아이템(=게시글=제목+본문+uid+시간) 목록
class BoardLVAdapter(val boardList : MutableList<BoardModel>) : BaseAdapter() {

    // 아이템 총 개수 반환
    override fun getCount(): Int = boardList.size

    // 아이템 반환
    override fun getItem(position: Int): Any = boardList[position]

    // 아이템의 아이디 반환
    override fun getItemId(position: Int): Long = position.toLong()

    // 아이템을 표시할 뷰 반환
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView

        // 레이아웃 인플레이터 -> 리사이클러뷰에서 뷰홀더 만들 때 반복적으로 사용
        view = LayoutInflater.from(parent?.context).inflate(R.layout.board_lv_item, parent, false)

        // 각 아이템뷰의 제목/본문/시간 영역에
        val title = view?.findViewById<TextView>(R.id.titleArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)

        // 제목, 시간 넣음
        title!!.text = boardList[position].title
        time!!.text = boardList[position].time

        // 현재 사용자가 작성한 글만 따로 표시하기 위해
        val myBoardBadge = view?.findViewById<TextView>(R.id.myBoardBadge)

        // 게시글 작성자의 uid와 현재 사용자의 uid가 일치하면 배지가 보이도록 처리
        myBoardBadge?.isVisible = boardList[position].uid.equals(FBAuth.getUid())

        // 뷰 반환
        return view!!

    }

}
```
```kotlin
// BoardWriteActivity.kt

    // 작성한 글을 등록
    private fun setBoard(key: String) {

        // 게시글의 데이터(제목, 본문, uid, 시간)
        val title = binding.titleArea.text.toString()
        val main = binding.mainArea.text.toString()
        val uid = FBAuth.getUid()
        val time = FBAuth.getTime()

        // 키 값 하위에 데이터 넣음
        FBRef.boardRef
            .child(key)
            .setValue(BoardModel(title, main, uid, time))

        // 카메라 아이콘을 클릭했다면 이미지 업로드
        if (isImageUpload == true) {

            // 이미지 파일명을 아무렇게나 설정하면 해당 게시글과 매칭하기 어려움
            // -> 키 값과 똑같이 설정하면 해결
            imageUpload("$key.png")

        }
        // 카메라 아이콘을 클릭하지 않음 -> 이미지를 업로드하지 않음

        // 등록 확인 메시지 띄움
        Toast.makeText(this, "게시글이 등록되었습니다", Toast.LENGTH_SHORT).show()

        // 글쓰기 액티비티 종료
        finish()

    }

    // 게시글에 이미지 첨부
    private fun imageUpload(key: String) {

        // Cloud Storage에 파일을 업로드하려면
        val storage = Firebase.storage

        // -> 우선 파일 이름을 포함하여 파일의 전체 경로를 가리키는 참조를 만듦
        val storageRef = storage.reference

        // 임의의 게시글 하나와 그 게시글 내 이미지 하나를 쉽게 매칭하려면
        // -> DB 내 게시글 키 값과 첨부한 이미지 이름이 똑같으면 됨
        val testRef = storageRef.child(key)

        // 적절한 참조를 만들었으면
        binding.imageArea.isDrawingCacheEnabled = true
        binding.imageArea.buildDrawingCache()
        val bitmap = (binding.imageArea.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        // -> put어쩌고() 메서드를 호출하여 Cloud Storage에 파일을 업로드
        var uploadTask = testRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.d(TAG, "imageUpload() failed")
        }.addOnSuccessListener { taskSnapshot ->
            // 성공
        }

    }

    // startActivityForResult와 세트
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100) {
            binding.imageArea.setImageURI(data?.data)
        }
    }
```
```kotlin
// BoardReadActivity.kt

        // 게시판 프래그먼트에서 게시글의 키 값을 받아옴
        key = intent.getStringExtra("key").toString()

        // 게시글 키 값을 바탕으로 게시글 하나의 정보를 가져옴
        getBoardData(key)
        getImageData(key)
        getCommentListData(key)

        // 게시글 설정 버튼
        binding.boardSettingBtn.setOnClickListener {

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(this, BoardEditActivity::class.java)

            // 키 값을 바탕으로 게시글 받아옴
            intent.putExtra("key", key)

            // 글수정 액티비티 시작
            startActivity(intent)

        }

    // 게시글에 첨부된 이미지 정보를 가져옴
    private fun getImageData(key: String) {

        // 이미지 파일 경로
        val storageReference = Firebase.storage.reference.child("$key.png")

        // 이미지 넣을 곳
        val imgDown = binding.imageArea

        // 글라이드로 이미지 다운로드
        storageReference.downloadUrl.addOnCompleteListener( { task ->

            // 이미지 첨부
            if(task.isSuccessful) {
                Glide.with(this)
                    .load(task.result)
                    .into(imgDown)
            // 첨부 이미지 없으면 imageArea 안 보이게 처리
            } else {
                binding.imageArea.isVisible = false
            }

        })

    }

    // 게시글 하나의 정보를 가져옴
    private fun getBoardData(key: String) {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 예외 처리
                try {

                    // 데이터 스냅샷 내 데이터모델 형식으로 저장된 아이템(=게시글)
                    val item = dataSnapshot.getValue(BoardModel::class.java)

                    // 제목, 시간, 본문 해당 영역에 넣음
                    binding.titleArea.text = item!!.title
                    binding.timeArea.text = item.time
                    binding.mainArea.text = item.main

                    // 게시글 작성자와 현재 사용자의 uid를 비교해
                    val writerUid = item.uid
                    val myUid = FBAuth.getUid()

                    // 작성자가 사용자면 수정 버튼 보임
                    binding.boardSettingBtn.isVisible = writerUid.equals(myUid)

                // 오류 나면
                } catch (e: Exception) {

                    // 로그
                    Log.d(TAG, "getBoardData 확인")

                }

            }
            // getBoardListData()와 달리 반복문이 아님 -> '단일' 아이템

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터의 변화(추가)를 알려줌
        FBRef.boardRef.child(key).addValueEventListener(postListener)

    }
```
```kotlin
// BoardEditActivity.kt

        // 글읽기 프래그먼트에서 게시글의 키 값을 받아옴
        key = intent.getStringExtra("key").toString()

        // 키 값을 바탕으로 게시글 하나의 정보를 가져옴
        getBoardData(key)

        // 키 값을 바탕으로 게시글에 첨부된 이미지 정보를 가져옴
        getImageData(key)

        // 수정하기 버튼 -> 키 값을 바탕으로 불러온 게시글 수정
        binding.boardEditBtn.setOnClickListener { editBoardData(key) }

        // 삭제하기 버튼 -> 키 값을 바탕으로 불러온 댓글 삭제
        binding.boardDeleteBtn.setOnClickListener { deleteBoardData(key) }

    // 게시글을 삭제
    private fun deleteBoardData(key: String) {

        // 게시글 삭제
        FBRef.boardRef.child(key).removeValue()

        // 삭제 확인 메시지
        Toast.makeText(this, "게시글이 삭제되었습니다", Toast.LENGTH_SHORT).show()

        // 게시글수정 액티비티 종료
        finish()

    }

    // 게시글을 수정
    private fun editBoardData(key: String) {

        // 수정한 값으로 업데이트
        FBRef.boardRef.child(key).setValue(BoardModel(

            // 제목 및 본문은 직접 수정한 내용으로,
            binding.titleArea.text.toString(),
            binding.mainArea.text.toString(),

            // uid와 시간은 자동 설정됨
            FBAuth.getUid(),
            FBAuth.getTime()

        ))

        // 수정 확인 메시지
        Toast.makeText(this, "게시글이 수정되었습니다", Toast.LENGTH_SHORT).show()

        // 글수정 액티비티 종료
        finish()

   }

    // 게시글에 첨부된 이미지 정보를 가져옴
    private fun getImageData(key: String) {

        // 중복코드 생략

    }

    // 게시글 하나의 정보를 가져옴
    private fun getBoardData(key: String) {

        // 중복코드 생략
        
    }
```

![board_comment](https://user-images.githubusercontent.com/68595933/192333287-c0932989-994e-466d-a798-fb3c88cbc156.PNG)

- 3.2 댓글 CRUD
  - 댓글 쓰기/읽기/수정/삭제가 가능합니다.
  - 내가 쓴 댓글만 수정/삭제 버튼 버튼을 나타냅니다.

```kotlin
// CommentModel.kt

// 게시글에 대한 정보를 데이터 모델 형태로 묶음
data class CommentModel (

    // 댓글 내용
    var main : String = "",

    // 작성자 uid
    var uid : String = "",

    // 작성 시간
    var time : String = ""

)
```
```kotlin
// BoardReadActivity.kt

        // 리스트뷰 어댑터 연결(댓글 목록)
        commentLVAdapter = CommentLVAdapter(commentList)
        val cLV : ListView = binding.commentLV
        cLV.adapter = commentLVAdapter

        // 댓글 목록(리스트뷰)
        cLV.setOnTouchListener(object : View.OnTouchListener {

            // 리스트뷰를 터치했을 때
            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                // 스크롤뷰(화면 전체)의 터치 이벤트를 막으면 -> 리스트뷰(댓글 영역)의 스크롤뷰가 작동함
                binding.boardReadSV.requestDisallowInterceptTouchEvent(true)
                return false

            }

        })

        // 게시판 프래그먼트에서 게시글의 키 값을 받아옴
        key = intent.getStringExtra("key").toString()

        // 게시글 키 값을 바탕으로 게시글 하나의 정보를 가져옴
        getBoardData(key)
        getImageData(key)
        getCommentListData(key)

        // 게시글 설정 버튼
        binding.boardSettingBtn.setOnClickListener {

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(this, BoardEditActivity::class.java)

            // 키 값을 바탕으로 게시글 받아옴
            intent.putExtra("key", key)

            // 글수정 액티비티 시작
            startActivity(intent)

        }

        // 파이어베이스의 댓글 키를 기반으로 댓글 데이터(=본문+uid+시간) 받아옴
        cLV.setOnItemClickListener { parent, view, position, id ->

            // 명시적 인텐트 -> 다른 액티비티 호출
            val intent = Intent(baseContext, CommentEditActivity::class.java)

            // 댓글수정 액티비티로 댓글의 키 값 전달
            intent.putExtra("key", key)

            // 댓글수정 액티비티로 댓글의 키 값 전달
            intent.putExtra("commentKey", commentKeyList[position])

            // 댓글수정 액티비티 시작
            startActivity(intent)

        }

        // 댓글쓰기 버튼
        binding.commentBtn.setOnClickListener {

            // -> 작성한 댓글을 등록
            setComment(key)

        }

    }

    // 댓글 목록 정보 가져옴
    private fun getCommentListData(key: String) {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 댓글 목록 비움
                // -> 저장/삭제 마다 데이터 누적돼 게시글 중복으로 저장되는 것 방지
                commentList.clear()

                // 데이터 스냅샷 내 데이터모델 형식으로 저장된
                for(dataModel in dataSnapshot.children) {

                    // 로그
                    Log.d(TAG, dataModel.toString())

                    // 아이템(=댓글)
                    val item = dataModel.getValue(CommentModel::class.java)

                    // 댓글 목록에 아이템 넣음
                    commentList.add(item!!)

                    // 댓글 키 목록에 문자열 형식으로 변환한 키 넣음
                    commentKeyList.add(dataModel.key.toString())

                }
                // 반복문임 -> 아이템'들'

                // 댓글 키 목록을 출력
                commentKeyList

                // 댓글 목록도 출력
                commentList

                // 댓글 헤더에 댓글 개수 출력
                binding.commentCountText.text = commentList.count().toString()

                // 동기화(새로고침) -> 리스트 크기 및 아이템 변화를 어댑터에 알림
                commentLVAdapter.notifyDataSetChanged()

            }

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터의 변화(추가)를 알려줌
        FBRef.commentRef.child(key).addValueEventListener(postListener)

    }

    // 작성한 댓글을 등록
    private fun setComment(key: String) {

        // 댓글의 데이터(본문, uid, 시간)
        val main = binding.commentMainArea.text.toString()
        val uid = FBAuth.getUid()
        val time = FBAuth.getTime()

        // 키 값 하위에 데이터 넣음
        FBRef.commentRef
            .child(key)
            .push()
            .setValue(CommentModel(main, uid, time))

        // 등록 확인 메시지 띄움
        Toast.makeText(this, "댓글이 등록되었습니다", Toast.LENGTH_SHORT).show()

        // 댓글 입력란 비움
        binding.commentMainArea.text = null

    }
```
```kotlin
// CommentLVAdapter.kt

    // 아이템 총 개수 반환
    override fun getCount(): Int = commentList.size

    // 아이템 반환
    override fun getItem(position: Int): Any = commentList[position]

    // 아이템의 아이디 반환
    override fun getItemId(position: Int): Long = position.toLong()

    // 아이템을 표시할 뷰 반환
    @SuppressLint("ViewHolder", "ClickableViewAccessibility")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView

        // 레이아웃 인플레이터 -> 리사이클러뷰에서 뷰홀더 만들 때 반복적으로 사용
        view = LayoutInflater.from(parent?.context).inflate(R.layout.comment_lv_item, parent, false)

        // 각 아이템뷰의 본문, 시간 영역에
        val commentMain = view?.findViewById<TextView>(R.id.commentMainArea)
        val commentTime = view?.findViewById<TextView>(R.id.commentTimeArea)

        // 본문, 시간 넣음
        commentMain!!.text = commentList[position].main
        commentTime!!.text = commentList[position].time

        // 댓글 작성자의 uid와 현재 사용자의 uid가 일치하면 댓글 배지가 보이도록 처리
        val myCommentBadge = view?.findViewById<TextView>(R.id.myCommentBadge)
        myCommentBadge?.isVisible = commentList[position].uid.equals(FBAuth.getUid())

        // 댓글 세팅 버튼도 동일함
        val commentSettingBtn = view?.findViewById<ImageView>(R.id.commentSettingBtn)
        commentSettingBtn?.isVisible = commentList[position].uid.equals(FBAuth.getUid())

        // 댓글 작성자의 uid와 현재 사용자의 uid가 다르면 터치 막음
        val commentLVItem = view?.findViewById<LinearLayout>(R.id.commentLVItemLayout)
        if(commentList[position].uid != FBAuth.getUid()) {
            commentLVItem?.setOnTouchListener(View.OnTouchListener { v, event -> true })
        }

        // 뷰 반환
        return view!!

    }
```
```kotlin
// CommentEditActivity.kt

        // 게시판 프래그먼트에서 게시글의 키 값을 받아옴
        key = intent.getStringExtra("key").toString()

        // 글읽기 액티비티에서 댓글의 키 값을 받아옴
        commentKey = intent.getStringExtra("commentKey").toString()

        // 댓글 키 값을 바탕으로 댓글 하나의 정보를 가져옴
        getCommentData(key, commentKey)

        // 수정하기 버튼 -> 키 값을 바탕으로 불러온 댓글 수정
        binding.commentEditBtn.setOnClickListener { editCommentData(key, commentKey) }

        // 삭제하기 버튼 -> 키 값을 바탕으로 불러온 댓글 삭제
        binding.commentDeleteBtn.setOnClickListener { deleteCommentData(key, commentKey) }

    // 댓글을 삭제
    private fun deleteCommentData(key: String, commentKey: String) {

        // 댓글 삭제
        FBRef.commentRef.child(key).child(commentKey).removeValue()

        // 삭제 확인 메시지
        Toast.makeText(this, "댓글이 삭제되었습니다", Toast.LENGTH_SHORT).show()

        // 댓글수정 액티비티 종료
        finish()

    }

    // 댓글을 수정
    private fun editCommentData(key: String, commentKey: String) {

        // 수정한 값으로 업데이트
        FBRef.commentRef.child(key).child(commentKey).setValue(CommentModel(

            // 제목 및 본문은 직접 수정한 내용으로,
            binding.commentMainArea.text.toString(),

            // uid와 시간은 자동 설정됨
            FBAuth.getUid(),
            FBAuth.getTime()

        ))

        // 수정 확인 메시지
        Toast.makeText(this, "댓글이 수정되었습니다", Toast.LENGTH_SHORT).show()

        // 댓글수정 액티비티 종료
        finish()

    }

    // 댓글 하나의 정보를 가져옴
    private fun getCommentData(key: String, commentKey: String) {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 예외 처리
                try {

                    // 데이터 스냅샷 내 데이터모델 형식으로 저장된 아이템(=게시글)
                    val item = dataSnapshot.getValue(CommentModel::class.java)

                    // 본문 해당 영역에 넣음(작성자 및 시간은 직접 수정하지 않음)
                    binding.commentMainArea.setText(item?.main)
                    // textView -> .text
                    // editText -> .setText(집어넣을 데이터)

                    // 오류 나면
                } catch (e: Exception) {

                    // 로그
                    Log.e(TAG, "getBoardData 확인")

                }

            }
            // getCommentListData()와 달리 반복문이 아님 -> '단일' 아이템

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터의 변화(추가)를 알려줌
        FBRef.commentRef.child(key).child(commentKey).addValueEventListener(postListener)

    }
```

### 4. 북마크

![bookmark](https://user-images.githubusercontent.com/68595933/192333390-f8367e6e-3c21-409d-9e69-03e35992a9ac.PNG)

- 컨텐츠 왼쪽 하단 하트 아이콘을 클릭해 북마크 추가(주황)/삭제(하양)가 가능합니다.

```kotlin
// ContentsRVAdapter.kt

            // 각 아이템뷰의 제목/썸네일/북마크(하트) 영역
            val contentsTitle = itemView.findViewById<TextView>(R.id.titleArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            // -> 북마크아이디리스트(=북마크된 아이템의 키 목록)에 화면에 표시된 아이템의 키 정보가 포함되면
            if(bookmarkIdList.contains(key)) {

                // 해당 아이템의 하트 -> 주황색
                bookmarkArea.setImageResource(R.drawable.bookmark56)

            // 포함되지 않으면
            } else {

                // 하트 -> 하얀색
                bookmarkArea.setImageResource(R.drawable.bookmark56w)

            }

            // 하트 클릭하면
            bookmarkArea.setOnClickListener {

                // bookmark_list 하위에 사용자 uid별로 나눠 게시글의 키 값을 저장

                // 이미 북마크 된 상태
                if(bookmarkIdList.contains(key)) {

                    // -> 북마크 삭제
                    FBRef.bookmarkRef
                        .child(FBAuth.getUid())
                        .child(key)
                        .removeValue()

                // 아직 북마크 안 된 상태
                } else {

                    // -> 북마크 저장
                    FBRef.bookmarkRef
                        .child(FBAuth.getUid())
                        .child(key)
                        .setValue(BookmarkModel(true))

                }

            }
```
```kotlin
// ContentsListActiviti.kt

    // 북마크 정보를 가져옴
    private fun getBookmarkData() {

        // 데이터베이스에서 컨텐츠의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // .clear() -> 북마크 아이디 목록 비움(저장/삭제 마다 데이터 누적되는 것 방지)
                bookmarkIdList.clear()

                // 데이터 스냅샷 내 데이터모델 형식으로 저장된
                for(dataModel in dataSnapshot.children) {

                    // 북마크 아이디 목록에 아이템의 키 값을 넣음
                    bookmarkIdList.add(dataModel.key.toString())

                }

                // 동기화(새로고침) -> 리스트 크기 및 아이템 변화를 어댑터에 알림
                rvAdapter.notifyDataSetChanged()

            }

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 데이터 변화(추가)를 알려줌
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)

    }
```
```kotlin
// BookmarkModel.kt

// 컨텐츠의 북마크 여부를 데이터 모델 형태로 묶음
data class BookmarkModel (

    // boolean 값이 true -> 북마크 된 컨텐츠
    val bookmarkOn : Boolean? = null

)
```
```kotlin
// BookmarkRVAdapter.kt

    // 뷰홀더 객체 생성 및 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkRVAdapter.Viewholder {

        // 레이아웃 인플레이터 -> 리사이클러뷰에서 뷰홀더 만들 때 반복적으로 사용
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent, false)

        // 아직 데이터는 들어가있지 않은 껍데기
        return Viewholder(v)

    }

    // 뷰홀더 객체와 데이터를 연결
    override fun onBindViewHolder(holder: BookmarkRVAdapter.Viewholder, position: Int) {

        // 껍데기(뷰홀더의 레이아웃)에 출력할 내용물(아이템 목록, 아이템의 키 목록)을 넣어줌
        holder.bindItems(items[position], keyList[position])

    }

    // 아이템들의 총 개수 반환
    override fun getItemCount(): Int = items.size

    // 각 아이템에 데이터 넣어줌
    inner class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        // 데이터 매핑(아이템, 아이템의 키)
        fun bindItems(item: ContentsModel, key: String) {

            // 리사이클러뷰는 setOnItemClickListener 없음 -> 개발자가 직접 구현해야 함

            // 아이템뷰(아이템 영역)를 클릭하면
            itemView.setOnClickListener {

                // 명시적 인텐트 -> 다른 액티비티 호출
                val intent = Intent(context, ContentsShowActivity::class.java)

                // 해당 아이템의 본문 url을 전달
                intent.putExtra("url", item.webUrl)

                // 컨텐츠쇼 액티비티 시작(웹뷰)
                itemView.context.startActivity(intent)

            }

            // 각 아이템뷰의 제목/썸네일/북마크(하트) 영역
            val contentsTitle = itemView.findViewById<TextView>(R.id.titleArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            // -> 북마크아이디리스트(=북마크된 아이템의 키 목록)에 화면에 표시된 아이템의 키 정보가 포함되면
            if(bookmarkIdList.contains(key)) {

                // 해당 아이템의 하트 -> 주황색
                bookmarkArea.setImageResource(R.drawable.bookmark56)

            // 포함되지 않으면
            } else {

                // 하트 -> 하얀색
                bookmarkArea.setImageResource(R.drawable.bookmark56w)

            }

            // 하트 클릭하면
            bookmarkArea.setOnClickListener {

                // bookmark_list 하위에 사용자 uid별로 나눠 게시글의 키 값을 저장

                // 이미 북마크 된 상태
                if(bookmarkIdList.contains(key)) {

                    // -> 북마크 삭제
                    FBRef.bookmarkRef
                        .child(FBAuth.getUid())
                        .child(key)
                        .removeValue()

                // 아직 북마크 안 된 상태
                } else {

                    // -> 북마크 저장
                    FBRef.bookmarkRef
                        .child(FBAuth.getUid())
                        .child(key)
                        .setValue(BookmarkModel(true))

                }

            }

            // 아이템의 제목 -> titleArea에 넣음
            contentsTitle.text = item.title

            // 아이템의 썸네일 -> 글라이드로 썸네일 이미지의 url을 imageViewArea에 넣음
            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)

        }

    }
```
```kotlin
// BookmarkFragment.kt

    // 블로그 탭의 모든 컨텐츠 가져옴
    private fun getBlogData() {

        // 데이터베이스에서 게시물의 세부정보를 검색
        val postListener = object : ValueEventListener {

            // 데이터 스냅샷
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                
                // 데이터 스냅샷 내 데이터모델 형식으로 저장된
                for(dataModel in dataSnapshot.children) {

                    // 모든 컨텐츠(키, 본문 url, 썸네일 url, 제목) 출력
                    Log.d(TAG, dataModel.toString())

                    // 아이템을 받아
                    val item = dataModel.getValue(ContentsModel::class.java)

                    // 북마크 아이디 목록에 키가 포함(북마크 저장)된 경우만
                    if(bookmarkIdList.contains(dataModel.key.toString())) {

                        // 아이템을 아이템 목록에 넣음
                        items.add(item!!)

                        // 키 값은 아이템 키 목록에 넣음
                        keyList.add(dataModel.key.toString())

                    }

                }

                // 동기화(새로고침) -> 리스트 크기 및 아이템 변화를 어댑터에 알림
                rvAdapter.notifyDataSetChanged()

            }

            // 오류 나면
            override fun onCancelled(databaseError: DatabaseError) {

                // 로그
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())

            }

        }

        // 파이어베이스 내 카테고리별 컨텐츠 데이터의 변화(추가)를 알려줌
        FBRef.androidStudio.addValueEventListener(postListener)
        FBRef.kotlinSyntax.addValueEventListener(postListener)
        FBRef.errorWarning.addValueEventListener(postListener)
        FBRef.vcsGithub.addValueEventListener(postListener)
        FBRef.webInternet.addValueEventListener(postListener)

    }
```

---

## DB 설계(Firebase Realtime Database)

![db_all](https://user-images.githubusercontent.com/68595933/193632949-208321a2-406a-44f9-a74f-a080957ddd73.png)

- DB 전체

![contents_table](https://user-images.githubusercontent.com/68595933/193634389-9d3b3996-76f3-4730-b2d1-e5344540e696.png)
![contents_table2](https://user-images.githubusercontent.com/68595933/193635085-38a90c71-82a7-461d-acc3-47a45682b1eb.png)

- android_studio, bookmark_list, error_warning, kotlin_syntax, vcs_github, web_internet(카테고리별 컨텐츠)

![board_table](https://user-images.githubusercontent.com/68595933/193635304-84a20c93-0b38-4157-9e9c-5639e5c74e64.png)

- board(게시판)

![comment_table](https://user-images.githubusercontent.com/68595933/193635663-3a01d761-8a5e-42fa-a33f-8d0801dcaad4.png)

- comment(댓글)

![bookmark_table](https://user-images.githubusercontent.com/68595933/193635838-efa530eb-768c-4660-82c2-e9806a7389c2.png)

- bookmark_list(북마크)

---

## 피드백

### 문제점

1. 댓글 개수 미구현.
2. 스크롤뷰 내 리스트뷰 삽입으로 인한 댓글출력 오류.

### 개선점

1. 22.09.21. 기준 게시글 내부에 댓글 개수 표시. 추후 게시글 목록에서도 댓글 개수가 보이도록 업데이트 할 것.
2. 22.09.28. 기준 댓글 스크롤 오류 해결.

---

## 저작권

이 프로젝트는 MIT 라이센스에 따라 라이센스가 부여됩니다. 자세한 내용은 [LICENSE.md](LICENSE.md) 파일을 참조하십시오.

---

## 레퍼런스

- [참고 강의 바로가기][참고]

<!-- 링크 -->

[참고]: https://www.inflearn.com/course/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%BD%94%ED%8B%80%EB%A6%B0-%EC%BB%A4%EB%AE%A4%EB%8B%88%ED%8B%B0%EC%95%B1/dashboard
