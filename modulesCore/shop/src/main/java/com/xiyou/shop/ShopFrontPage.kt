package com.xiyou.shop

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.xiyou.shop.entite.ShopItem
import com.xiyou.shop.ui.theme.Shapes

@ExperimentalFoundationApi
@Composable
fun ShopFrontPage(viewModel: ShopViewModel = androidx.lifecycle.viewmodel.compose.viewModel())
{
    val list by viewModel.allItem.collectAsState(initial = emptyList())
    LazyVerticalGrid(cells = GridCells.Fixed(2)){
        items(list){item->
            ShopItemCard(item)
        }
    }
}


@Composable
fun ShopItemCard(item: ShopItem){
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable(enabled = true) {},
        shape = RoundedCornerShape(12.dp)) {
        Column {
            AsyncImage(
                model = item.image, contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )
            Text(text = item.title, fontSize = 16.sp, maxLines = 2)
            Row(
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = item.price.toString(), color = Color.Red,
                    fontSize = 16.sp
                )
                Text(
                    text = "${item.sellCount}人付款", fontSize = 10.sp, color = Color.Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
@Preview
@Composable
fun preViewShopFrontPage(){
    ShopItemCard(item = ShopItem(image = "https://img.alicdn.com/bao/uploaded/i2/1683458694/O1CN019Y5QMS2E5u1RGns7S_!!1683458694.jpg_468x468q75.jpg_.webp",
        title = "JP健身裤男春夏高腰专业压缩打底裤弹力紧身跑步透气速干运动健美", price = 142.20, sellCount = 40, shopName = "MSK flame运动健身馆")
    )
}