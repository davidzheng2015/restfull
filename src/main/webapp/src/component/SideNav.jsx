import React from 'react';
import {
  Menu, Icon
}
from 'antd';

function generateMenu(o) {
  let arr = o.map(function(item) {
    if (item.children) {
      return <SubMenu key={item.id} title={item.name}>{generateMenu(item.children)}</SubMenu>
      generateMenu(item.children);
    } else {
      return <Menu.Item key={item.id}><a href={item.url}>{item.name}</a></Menu.Item>
    }
  });
  return arr;
}
const SubMenu = Menu.SubMenu;
const SideNav = React.createClass({
  getInitialState() {
      return {
        current: '1',
        openKeys: []
      };
    },
    //load:this.props.load , //'lazy','all'
    handleClick(e) {
      this.setState({
        current: e.key,
        openKeys: e.keyPath.slice(1)
      });
    },
    onToggle(info) {
      this.setState({
        openKeys: info.open ? info.keyPath : info.keyPath.slice(1)
      });
    },
    render:function() {
      let treeHtml = generateMenu(this.props.data);
      return (
        <Menu onClick={this.handleClick}
                 openKeys={this.state.openKeys}
                 onOpen={this.onToggle}
                 onClose={this.onToggle}
                 selectedKeys={[this.state.current]}
                 mode="inline">
        {treeHtml}      
        </Menu>
      );
    }
});

export default SideNav;