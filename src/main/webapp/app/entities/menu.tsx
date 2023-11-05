import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/number-of-hours">
        <Translate contentKey="global.menu.entities.numberOfHours" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/daily-calls">
        <Translate contentKey="global.menu.entities.dailyCalls" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/daily-chats">
        <Translate contentKey="global.menu.entities.dailyChats" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
