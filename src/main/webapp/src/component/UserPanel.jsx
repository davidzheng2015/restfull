import React from 'react';
import {
	Menu, Dropdown, Icon
}
from 'antd';
const menu = <Menu>
	         <Menu.Item key="0"><a href="/url/profilo">个人资料</a></Menu.Item>
  			 <Menu.Divider/>
  			 <Menu.Item key="1"><a href="/url/logut">退出帐号</a></Menu.Item> 
   			</Menu>;
		
const UserPanel = React.createClass({
	render: function() {
		return (
			<div>
			<Icon type="user" style={{fontSize:'18px'}}/>
			<Dropdown overlay={menu} trigger={['click']}>
             <a href="#"> 注册管理科 - 陈鹏 <Icon type="circle-o-down" /></a>
            </Dropdown>
            </div>
		);
	}
});

export default UserPanel;