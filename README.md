# 신예리의 개발전기 Develography

> 커뮤니티 앱 프로젝트

![develography_cover](https://user-images.githubusercontent.com/68595933/192332560-a99407ab-1ac5-4e55-a3ca-c01edcf795cc.PNG)

## 시작

- 블로그를 활용한 커뮤니티 앱 프로젝트입니다.

---

## 개발

### 기간

- 22.08.02.~22.00.00.

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
  - 로그인
  - 비회원로그인
  - 로그아웃

```kotlin
// FBAuth.kt

// 클래스 인스턴스 없이 클래스 내부에 접근하려면
class FBAuth {

    // -> 클래스 내부에 객체 선언할 때 동반 객체로 선언
    companion object {

        // FirebaseAuth의 인스턴스를 선언
        private lateinit var auth: FirebaseAuth

        // getUid(), getTime() -> 편의상 FBAuth.kt에 분리함

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

    }

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
  - 회원가입

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

### 웹뷰

![webview](https://user-images.githubusercontent.com/68595933/192333063-c18a7ff9-32bf-449b-bdfe-225eb456ec2a.PNG)

- 웹뷰

```kotlin
// FBRef.kt

// 클래스 인스턴스 없이 클래스 내부에 접근하려면
class FBRef {

    // -> 클래스 내부에 객체 선언할 때 동반 객체로 선언
    companion object {

        // 파이어베이스
        private val database = Firebase.database

        // .getReference() -> 데이터베이스의 루트 폴더 주소 값을 반환

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

    }

}
```

```kotlin
// ContentsModel.kt

    // 블로그 컨텐츠의 제목
    var title : String = "",

    // 블로그 컨텐츠의 썸네일 이미지 url
    var imageUrl : String = "",

    // 블로그 컨텐츠의 본문 url
    var webUrl : String = ""
```
```kotlin
// ContentsRVAdapter.kt

class ContentsRVAdapter(
    val context: Context, // 컨텍스트
    val items: ArrayList<ContentsModel>, // 아이템(=컨텐츠=제목+썸네일+본문) 목록
    val keyList: ArrayList<String>, // 아이템의 키 목록
    val bookmarkIdList: MutableList<String> // 북마크의 아이디(키) 목록
): RecyclerView.Adapter<ContentsRVAdapter.Viewholder>() {
// 리사이클러뷰의 어댑터 -> RecyclerView.Adapter를 상속해서 구현

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

        // 파이어베이스에 (웹뷰에서 보여줄) 아이템 데이터 넣기
//        val myRef1 = database.getReference("파이어베이스 카테고리 이름")

        // 제목, 썸네일, 본문 순으로 들어감(파이어베이스)
//        myRef1.push().setValue(
//            ContentsModel(
//                "컨텐츠 제목",
//                "썸네일 URL",
//                "본문 URL")
//        )

        // 뒤로가기 버튼 -> 컨텐츠리스트 액티비티 종료
        binding.backBtn.setOnClickListener {
            finish()
        }

// 중략

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

        // 에러 아이콘 -> error_warning
        binding.errorIcon.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("category", "error_warning")
            startActivity(intent)
        }

        // 버전컨트롤시스템 아이콘 -> vcs_github
        binding.vcsIcon.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("category", "vcs_github")
            startActivity(intent)
        }

        // 웹 아이콘 -> category
        binding.webIcon.setOnClickListener {
            val intent = Intent(context, ContentsListActivity::class.java)
            intent.putExtra("category", "web_internet")
            startActivity(intent)
        }
```
```kotlin
// WebFragment.kt

        // 웹뷰에 깃허브 링크 넣음
        binding.webView.loadUrl("https://github.com/shinyelee")
```

### 게시판

![board_write](https://user-images.githubusercontent.com/68595933/192333230-07034857-d16b-4eb3-9ba7-42b56f2bd293.PNG)
![board_comment](https://user-images.githubusercontent.com/68595933/192333287-c0932989-994e-466d-a798-fb3c88cbc156.PNG)

- 글 CRUD
- 댓글 CRUD

```kotlin
// 글 CRUD
```
```kotlin
// 글 CRUD
```

### 북마크

![bookmark](https://user-images.githubusercontent.com/68595933/192333390-f8367e6e-3c21-409d-9e69-03e35992a9ac.PNG)

- 북마크 추가
- 북마크 삭제

```kotlin
// 북마크 추가/삭제
```

---

## 피드백

### 문제점

1. ~함.

### 개선점

1. ~할 것.

---

## 저작권

이 프로젝트는 MIT 라이센스에 따라 라이센스가 부여됩니다. 자세한 내용은 [LICENSE.md](LICENSE.md) 파일을 참조하십시오.

---

## 레퍼런스

- [참고 강의 바로가기][참고]

<!-- 링크 -->

[참고]: https://www.inflearn.com/course/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%BD%94%ED%8B%80%EB%A6%B0-%EC%BB%A4%EB%AE%A4%EB%8B%88%ED%8B%B0%EC%95%B1/dashboard
