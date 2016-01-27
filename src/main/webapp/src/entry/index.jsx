import '../common/lib';
import React from 'react';
import ReactDOM from 'react-dom';
import SideNav from '../component/SideNav';
import Header from '../component/Header';
import obj from '../../mock/data';
import {
	Menu, Dropdown, Icon
}
from 'antd';



ReactDOM.render(<SideNav data={obj.data} />, document.getElementById('side-nav'));
ReactDOM.render(<Header/>, document.getElementById('header'));