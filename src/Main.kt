import com.mysql.jdbc.Driver
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.sql.DriverManager
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JTextField
/*
* 数据库实验1的Kotlin实现
* 附注释
* */
//连接MySql数据库 需要用到"com.mysql:mysql-connector-j:8.3.0"
//获取statement 就是可以输入SQL指令的类
val statement = run {
    //前两行复制下来的 我也不知道为什么......
    val driver: Driver = Driver()
    DriverManager.registerDriver(driver)
    //目标数据库的URL连接
    val url = "jdbc:mysql://127.0.0.1:3303/homework"
    //目标数据库的管理员用户名
    val user = "root"
    //目标数据库的管理员密码
    val password = "123456"
    //连接数据库 创建连接
    val conn = DriverManager.getConnection(url, user, password)
    //获取statement 就是可以输入SQL指令的类
    conn.createStatement()
}
//通过用户名和密码生成查询的SQL语句
fun generateSqlCode(username: String, password: String) :String{
    //$是Kotlin的语法糖 是插值的意思 把username和password的这两个参数插到sql语句的相应位置
    return "select * from users where username='${username}' and password='$password'"
}
fun main() {
    //新建一个窗口对象
    val frame = JFrame("数据库实验1")
    //设置窗口宽高为500x500
    frame.setSize(500, 500)
    //当窗口被关闭的时候 程序结束
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.layout = null
    //创建用户名提示
    val usernameLabel = JLabel("用户名")
    //设置该组件应该位于的窗口位置,X=50,Y=50,Length=100,Height=70
    usernameLabel.setBounds(50, 50, 100, 70)
    //创建用户名输入框
    val usernameField = JTextField("")
    usernameField.setBounds(150, 50, 300, 70)
    frame.add(usernameLabel)
    frame.add(usernameField)
    //将两个组件添加到窗口
    //同上 创建密码输入框
    val passwordLabel = JLabel("密码")
    passwordLabel.setBounds(50, 150, 100, 70)
    val passwordField = JTextField("")
    passwordField.setBounds(150, 150, 300, 70)
    frame.add(passwordLabel)
    frame.add(passwordField)
    //创建一个按钮来生成SQL代码
    val generateCodeButton = JButton("生成SQL代码")
    generateCodeButton.setBounds(50, 350, 150, 50)
    frame.add(generateCodeButton)
    //同上 创建一个按钮模拟登录效果
    val loginButton = JButton("登录")
    loginButton.setBounds(300, 350, 150, 50)
    frame.add(loginButton)
    //将生成代码事件绑定到generateCodeButton按钮上
    generateCodeButton.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent?) {
            super.mouseClicked(e)
            //获取两个输入框中的用户名和密码
            val username = usernameField.text
            val password = passwordField.text
            //根据用户名和密码生成sql查询语句 并通过弹窗进行提示
            JOptionPane.showMessageDialog(null,"SQL代码:${generateSqlCode(username,password)}")
        }
    })
    //将模拟登录事件绑定到loginButton按钮上
    loginButton.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent?) {
            super.mouseClicked(e)
            //获取两个输入框中的用户名和密码
            val username = usernameField.text
            val password = passwordField.text
            //获取到根据username和password生成的SQL语句
            val sqlCode=generateSqlCode(username,password)
            val query=statement.executeQuery(sqlCode)
            //如果query有下一个条目 则证明数据库中有数据符合该用户名和密码 也就是说存在该账号 登录成功
            if(query.next()){
                //提示登陆失败的信息
                JOptionPane.showMessageDialog(null,"登录成功!")
            }else{
                //若query查询结果中无条目 则证明数据库中没有匹配该用户名和密码的行 登录失败
                JOptionPane.showMessageDialog(null,"登录失败 用户名或密码错误")
            }
        }
    })
    //显示窗口
    frame.isVisible = true
    //更新窗口界面
    frame.validate()
}