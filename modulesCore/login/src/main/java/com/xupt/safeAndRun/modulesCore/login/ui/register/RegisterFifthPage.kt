package com.xupt.safeAndRun.modulesCore.login.ui.register

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.xupt.safeAndRun.modulespublic.common.R
import com.xupt.safeAndRun.modulespublic.common.constant.RoutePath
import com.xupt.safeAndRun.modulesCore.login.mvvm.RegisterViewModel
import com.xupt.safeAndRun.modulesCore.login.util.findActivity
import com.yalantis.ucrop.UCrop
import java.io.File


@Composable
fun RegisterFifthPage(
     registerViewModel: RegisterViewModel = viewModel(),
     navController: NavHostController = rememberNavController()
){

    var url by remember { mutableStateOf(Uri.EMPTY)}
    var isRightImage by remember {
        mutableStateOf(false)
    }

    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

    val context = LocalContext.current.findActivity()!!

    val cropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            isRightImage = true
        })

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            url = it?.data?.data ?: Uri.EMPTY
            val file = File(context.cacheDir, File(url.path).name)
            if(file.exists())
                file.delete()

            registerViewModel.head = Uri.fromFile(file)

            val intent = UCrop.of(url, registerViewModel.head)
                .withAspectRatio(1f, 1f)
                .getIntent(context)
            isRightImage = false
            cropLauncher.launch(intent)
        }
    )
    Scaffold(
        bottomBar = {
            BottomAppBar(
                cutoutShape = MaterialTheme.shapes.small.copy(
                    CornerSize(percent = 50)
                )
            ) {
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                registerViewModel.setUserInfo() {
                    navController.navigate("com.xupt.safeAndRun.runActivity")
                }
            }){
                Icon(Icons.Filled.ArrowForward, contentDescription = "next")
            }
        },
        content = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(it)
                .padding(horizontal = 24.dp)
                .padding(top = 54.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "现在, 来挑选一个好看的头像吧")
                if(!isRightImage)
                    Image(
                        painter = painterResource(id = R.mipmap.empty_image),
                        contentDescription = "空图像",
                        modifier = Modifier.size(100.dp)
                    )
                else
                    AsyncImage(model = registerViewModel.head,
                    contentDescription = "头像预览",
                    modifier = Modifier.size(100.dp)
                    )
                Button(onClick = { launcher.launch(intent) }) {
                    Text(text =
                    if (TextUtils.isEmpty(url.toString()))
                        "选择图片"
                    else
                        "重新选择"
                        )
                }
            }
        }
    )
}
