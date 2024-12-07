package maingame;

import entity.Entity;

public class collisions {
    GamePanel gp;

    public collisions(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidAreaDefaultX;
        int entityRightWorldX = entity.worldX + entity.solidAreaDefaultX + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidAreaDefaultY;
        int entityBottomWorldY = entity.worldY + entity.solidAreaDefaultY + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        for(int i = 0; i < gp.obj.length; i++) {
            if(gp.obj[i] != null) {
                // Save original positions
                int originalEntityX = entity.solidArea.x;
                int originalEntityY = entity.solidArea.y;
                int originalObjX = gp.obj[i].solidArea.x;
                int originalObjY = gp.obj[i].solidArea.y;

                // Position
                entity.solidArea.x = entity.worldX + entity.solidAreaDefaultX;
                entity.solidArea.y = entity.worldY + entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidAreaDefaultY;

                switch(entity.direction) {
                    case "up": entity.solidArea.y -= entity.speed; break;
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;
                }

                if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    if(gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if(player) {
                        index = i;
                    }
                }

                // Reset
                entity.solidArea.x = originalEntityX;
                entity.solidArea.y = originalEntityY;
                gp.obj[i].solidArea.x = originalObjX;
                gp.obj[i].solidArea.y = originalObjY;
            }
        }
        return index;
    }

    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;
        for(int i = 0; i < target.length; i++) {
            if(target[i] != null) {
                int originalEntityX = entity.solidArea.x;
                int originalEntityY = entity.solidArea.y;
                int originalTargetX = target[i].solidArea.x;
                int originalTargetY = target[i].solidArea.y;

                entity.solidArea.x = entity.worldX + entity.solidAreaDefaultX;
                entity.solidArea.y = entity.worldY + entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].worldX + target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].worldY + target[i].solidAreaDefaultY;

                switch(entity.direction) {
                    case "up": entity.solidArea.y -= entity.speed; break;
                    case "down": entity.solidArea.y += entity.speed; break;
                    case "left": entity.solidArea.x -= entity.speed; break;
                    case "right": entity.solidArea.x += entity.speed; break;
                }

                if(entity.solidArea.intersects(target[i].solidArea)) {
                    entity.collisionOn = true;
                    index = i;
                }

                entity.solidArea.x = originalEntityX;
                entity.solidArea.y = originalEntityY;
                target[i].solidArea.x = originalTargetX;
                target[i].solidArea.y = originalTargetY;
            }
        }
        return index;
    }

    public void checkPlayer(Entity entity) {
        int originalEntityX = entity.solidArea.x;
        int originalEntityY = entity.solidArea.y;
        int originalPlayerX = gp.player.solidArea.x;
        int originalPlayerY = gp.player.solidArea.y;

        entity.solidArea.x = entity.worldX + entity.solidAreaDefaultX;
        entity.solidArea.y = entity.worldY + entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidAreaDefaultY;

        switch(entity.direction) {
            case "up": entity.solidArea.y -= entity.speed; break;
            case "down": entity.solidArea.y += entity.speed; break;
            case "left": entity.solidArea.x -= entity.speed; break;
            case "right": entity.solidArea.x += entity.speed; break;
        }

        if(entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
        }

        entity.solidArea.x = originalEntityX;
        entity.solidArea.y = originalEntityY;
        gp.player.solidArea.x = originalPlayerX;
        gp.player.solidArea.y = originalPlayerY;
    }
}
