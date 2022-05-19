package com.xiyou.shop

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.xiyou.shop.entite.ShopItem

@OptIn(ExperimentalFoundationApi::class)
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
    Card() {
        Column(modifier = Modifier.padding(8.dp))
        {
            AsyncImage(model = item.image, contentDescription = item.title)
            Text(text = item.title, fontSize = 12.sp)
            Row() {
                Text(text = item.price.toString())
                Text(text = item.sellCount.toString())
            }
        }
    }
}
@Preview
@Composable
fun preViewShopFrontPage(){
    ShopFrontPage()
}