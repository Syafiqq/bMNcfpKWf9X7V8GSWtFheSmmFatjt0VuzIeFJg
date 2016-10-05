package model.pso.core.test;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import java.util.Arrays;
import main.Main;
import model.dataset.DatasetP2Generator3;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset2;
import model.dataset.core.DatasetConverter;
import model.dataset.core.WorkingSet;
import model.pso.component.Data;
import model.pso.component.ParticleP2;
import model.pso.component.Position;
import model.pso.component.Setting;
import model.pso.core.PSOP2;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <Skripsi_003> project in package <model.pso.core.test> created by : 
 * Name         : syafiq
 * Date / Time  : 24 August 2016, 3:00 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class PSOP2TestForRepair
{
    final Setting setting = Setting.getInstance();
    private Dataset2<Timeoff, Lesson>                  dataset;
    private WorkingSet                                 workingset;
    private DatasetConverter<Int2IntLinkedOpenHashMap> encoder;
    private DatasetConverter<Int2IntLinkedOpenHashMap> decoder;
    private DatasetP2Generator3                        gen;
    private PSOP2                                      pso;

    @SuppressWarnings("Duplicates") public void generateDataset()
    {
        Main.getMyDatabaseAccount();
        this.dataset = new Dataset2<>(1);
        this.workingset = new WorkingSet();
        this.encoder = new DatasetConverter<>();
        this.decoder = new DatasetConverter<>();
        this.gen = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
    }

    @Test
    public void testRepair()
    {
        this.setting.bglob_min = 0.4;
        this.setting.bglob_max = 0.6;
        this.setting.bloc_min = 0.7;
        this.setting.bloc_max = 0.9;
        this.setting.brand_min = 0.001;
        this.setting.brand_max = 0.01;
        this.setting.total_core = 3;
        this.setting.max_particle = 10;
        this.setting.max_epoch = 5000;

        this.generateDataset();
        this.pso = new PSOP2(this.setting, this.gen);
        this.pso.initializeSwarm();
        Data.replacePositon(pso.particles[0].data.positions, new Position[] {
                new Position(new int[] {587, 650, 585, 646, 653, 651, 0, 0, 0, 687, 647, 583, 652, 586, 689, 690, 0, 550, 547, 691, 584, 548, 649, 0, 644, 0, 686, 0, 0, 0, 688, 645, 588, 0, 0, 0, 549, 0, 0, 0, 546, 0, 648, 0, 0, 0, 0}),
                new Position(new int[] {581, 682, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 580, 683, 579, 684, 582, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 685, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
                new Position(new int[] {259, 257, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 251, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 250, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 252, 253, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 256, 258, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}),
                new Position(new int[] {314, 310, 303, 308, 301, 311, 302, 0, 312, 309, 307, 305, 313, 20, 83, 0, 300, 304, 3, 84, 7, 12, 22, 17, 0, 15, 18, 8, 21, 80, 14, 71, 1, 0, 19, 82, 74, 24, 0, 306, 0, 76, 0, 0, 81, 75, 70, 73, 13, 0, 0, 0, 0, 0, 0, 78, 26, 25, 0, 0, 0, 0, 10, 0, 4, 72, 9, 23, 77, 0, 0, 2, 0, 27, 0, 0, 5, 11, 0, 0, 0, 0, 0, 0, 0, 0, 79, 6, 0, 0, 16}),
                new Position(new int[] {207, 376, 552, 562, 558, 0, 384, 571, 363, 392, 395, 451, 60, 229, 262, 489, 560, 453, 0, 428, 556, 411, 184, 101, 170, 63, 110, 361, 62, 414, 432, 572, 144, 516, 360, 294, 341, 469, 279, 40, 633, 559, 0, 452, 660, 0, 160, 93, 0, 661, 148, 162, 519, 540, 518, 156, 398, 615, 183, 0, 224, 664, 0, 543, 344, 668, 66, 619, 0, 155, 358, 208, 443, 94, 263, 387, 261, 367, 233, 388, 107, 167, 180, 442, 641, 0, 92, 119, 380, 171, 349, 370, 231, 157, 640, 418, 0, 609, 509, 348, 276, 326, 283, 383, 483, 403, 554, 482, 0, 382, 607, 0, 541, 56, 0, 130, 603, 368, 476, 505, 97, 0, 517, 260, 0, 625, 347, 0, 526, 284, 0, 95, 291, 485, 570, 0, 169, 206, 534, 102, 0, 39, 663, 365, 122, 209, 521, 0, 448, 281, 178, 450, 404, 226, 491, 475, 0, 213, 401, 343, 164, 672, 150, 366, 0, 112, 608, 106, 88, 386, 422, 325, 111, 50, 477, 676, 67, 0, 416, 132, 445, 446, 211, 617, 0, 444, 99, 273, 618, 429, 555, 0, 334, 484, 677, 381, 0, 389, 89, 103, 55, 654, 642, 566, 134, 372, 235, 163, 0, 234, 133, 147, 553, 0, 409, 391, 346, 104, 222, 506, 0, 669, 0, 139, 355, 629, 435, 296, 52, 576, 161, 574, 405, 466, 598, 0, 479, 189, 0, 589, 665, 551, 152, 0, 674, 151, 407, 670, 0, 439, 375, 280, 64, 57, 510, 0, 457, 96, 402, 592, 0, 614, 0, 542, 357, 616, 223, 121, 397, 98, 330, 58, 412, 165, 0, 228, 627, 220, 359, 463, 292, 406, 329, 33, 430, 467, 61, 561, 568, 563, 447, 221, 373, 108, 297, 436, 212, 203, 0, 282, 174, 227, 673, 225, 345, 120, 622, 0, 138, 369, 0, 630, 272, 0, 427, 342, 141, 544, 114, 331, 364, 441, 680, 0, 42, 90, 493, 0, 417, 333, 611, 181, 0, 100, 293, 532, 264, 511, 0, 51, 390, 179, 36, 465, 166, 159, 529, 557, 666, 472, 0, 116, 339, 434, 168, 143, 0, 567, 0, 237, 421, 371, 105, 501, 0, 393, 655, 124, 573, 0, 29, 480, 632, 0, 494, 527, 638, 399, 423, 604, 0, 449, 176, 385, 173, 374, 474, 569, 0, 394, 524, 142, 140, 68, 438, 350, 0, 41, 656, 0, 455, 0, 0, 495, 0, 49, 205, 0, 131, 468, 158, 659, 0, 197, 0, 419, 242, 135, 149, 0, 637, 0, 594, 332, 0, 596, 0, 286, 145, 0, 488, 636, 0, 191, 0, 0, 0, 400, 639, 379, 0, 0, 545, 0, 54, 146, 289, 69, 0, 196, 0, 271, 186, 0, 0, 0, 478, 86, 671, 118, 0, 601, 322, 0, 340, 315, 490, 268, 454, 46, 624, 115, 194, 299, 0, 290, 129, 287, 503, 113, 0, 620, 0, 528, 172, 0, 218, 600, 0, 275, 426, 520, 0, 597, 460, 0, 190, 0, 335, 565, 0, 514, 537, 606, 65, 295, 675, 356, 85, 0, 267, 241, 154, 500, 0, 0, 522, 610, 0, 678, 0, 117, 539, 0, 396, 498, 0, 319, 415, 137, 377, 193, 0, 634, 0, 136, 536, 0, 249, 0, 0, 0, 413, 667, 433, 0, 0, 0, 590, 0, 662, 0, 202, 0, 508, 0, 657, 0, 328, 440, 535, 0, 564, 658, 0, 0, 216, 274, 182, 502, 362, 0, 504, 0, 123, 512, 0, 531, 87, 0, 533, 0, 185, 578, 219, 538, 0, 577, 0, 628, 631, 523, 408, 59, 525, 0, 327, 378, 232, 187, 0, 492, 0, 245, 199, 0, 0, 0, 530, 0, 0, 599, 0, 336, 200, 0, 91, 513, 0, 201, 0, 626, 48, 204, 321, 0, 44, 497, 210, 0, 285, 230, 277, 240, 238, 681, 35, 0, 605, 0, 192, 461, 0, 127, 410, 0, 0, 0, 424, 354, 198, 623, 499, 243, 320, 188, 487, 177, 318, 265, 462, 239, 316, 593, 0, 153, 0, 612, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 470, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 195, 0, 0, 0, 215, 0, 507, 0, 0, 0, 0, 43, 0, 0, 0, 0, 0, 0, 591, 0, 515, 0, 34, 0, 0, 0, 0, 0, 0, 0, 0, 602, 0, 0, 0, 0, 0, 0, 0, 270, 471, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 643, 0, 575, 0, 175, 0, 0, 0, 0, 0, 0, 0, 496, 679, 0, 0, 0, 0, 613, 595, 0, 0, 0, 0, 47, 621, 0, 0, 635, 0, 0, 0, 0, 0, 45, 0, 337, 0, 0, 0, 425, 0, 0, 0, 217, 0, 0, 0, 0, 0, 0, 317, 0, 0, 125, 0, 0, 37, 0, 31, 0, 0, 0, 53, 0, 0, 486, 0, 352, 0, 0, 0, 38, 0, 0, 0, 266, 0, 0, 0, 0, 0, 456, 0, 458, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 278, 0, 0, 0, 0, 0, 32, 30, 0, 0, 0, 246, 481, 248, 0, 0, 0, 353, 0, 0, 214, 0, 109, 247, 459, 269, 420, 0, 0, 0, 28, 0, 0, 0, 0, 0, 0, 431, 0, 0, 0, 351, 0, 0, 0, 324, 0, 0, 126, 0, 0, 0, 0, 0, 0, 0, 0, 338, 0, 0, 0, 464, 0, 0, 0, 0, 0, 0, 128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 244, 298, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 437, 0, 323, 0, 0, 236, 0, 0, 0, 0, 0, 0, 473, 0, 0, 288})
        });
        pso.calculateFitness(pso.particles[0]);
        System.out.println(pso.particles[0].data.fitness);
        pso.repairData(pso.particles[0]);
        pso.calculateFitness(pso.particles[0]);
        System.out.println(pso.particles[0].data.fitness);
        //pso.repairData(pso.particles[0]);
        //pso.calculateFitness(pso.particles[0]);
        //System.out.println(pso.particles[0].data.fitness);
    }

    @Test public void findRepairError()
    {
        this.setting.bglob_min = 0.4;
        this.setting.bglob_max = 0.6;
        this.setting.bloc_min = 0.7;
        this.setting.bloc_max = 0.9;
        this.setting.brand_min = 0.001;
        this.setting.brand_max = 0.01;
        this.setting.total_core = 3;
        this.setting.max_particle = 10;
        this.setting.max_epoch = 5000;

        int error = 0;

        this.generateDataset();
        this.pso = new PSOP2(this.setting, this.gen);
        pso.initializeSwarm();
        pso.updateSwarmFitness();
        while(!pso.isConditionSatisfied())
        {
            pso.updateAllParticlePBest();
            pso.assignGBest();
            pso.evaluateAllParticle();
            pso.updateStoppingCondition();
            final ParticleP2 suspect = pso.particles[0];
            final Position[] copy    = new Position[suspect.data.positions.length];
            for(int i = -1, is = copy.length; ++i < is; )
            {
                copy[i] = Position.newInstance(Arrays.copyOf(suspect.data.positions[i].position, suspect.data.positions[i].position.length));
            }
            Data.replacePositon(suspect.data.positions, pso.gBest.positions);
            pso.calculateFitness(suspect);
            double aFitness = suspect.data.fitness;
            pso.repairData(suspect);
            pso.calculateFitness(suspect);
            double  bFitness   = suspect.data.fitness;
            boolean isNotError = (aFitness == bFitness);
            error += (isNotError ? 0 : 1);
            System.out.printf("%f %f = %b\n", aFitness, bFitness, isNotError);
            Data.replacePositon(suspect.data.positions, copy);
        }
        System.out.println(pso.gBest.fitness);
        System.out.println("error = " + error);
        //pso.doExchange();
        //System.out.println(pso.gBest.fitness);
    }

    @Test public void getRepairError()
    {
        this.setting.bglob_min = 0.4;
        this.setting.bglob_max = 0.6;
        this.setting.bloc_min = 0.7;
        this.setting.bloc_max = 0.9;
        this.setting.brand_min = 0.001;
        this.setting.brand_max = 0.01;
        this.setting.total_core = 3;
        this.setting.max_particle = 10;
        this.setting.max_epoch = 5000;

        this.generateDataset();
        this.pso = new PSOP2(this.setting, this.gen);
        pso.initializeSwarm();
        pso.updateSwarmFitness();
        while(!pso.isConditionSatisfied())
        {
            pso.updateAllParticlePBest();
            pso.assignGBest();
            pso.evaluateAllParticle();
            pso.updateStoppingCondition();
            final ParticleP2 suspect = pso.particles[0];
            Data.replacePositon(suspect.data.positions, pso.gBest.positions);
            pso.calculateFitness(suspect);
            double aFitness = suspect.data.fitness;
            pso.repairData(suspect);
            pso.calculateFitness(suspect);
            double  bFitness   = suspect.data.fitness;
            boolean isNotError = (aFitness == bFitness);
            if(!isNotError)
            {
                System.out.printf("%f %f = %b\n", aFitness, bFitness, isNotError);
                System.out.println(suspect.data.toString(0));
                System.out.println("=====");
                System.out.println(pso.gBest.toString(0));
                System.out.println("=====");
                for(int i = -1, is = suspect.data.positions.length; ++i < is; )
                {
                    System.out.printf("Position %d = %b\n", i, Arrays.equals(suspect.data.positions[i].position, pso.gBest.positions[i].position));
                }
                break;
            }
        }
    }

    @Test public void getRepairErrorFormattedReady()
    {
        this.setting.bglob_min = 0.4;
        this.setting.bglob_max = 0.6;
        this.setting.bloc_min = 0.7;
        this.setting.bloc_max = 0.9;
        this.setting.brand_min = 0.001;
        this.setting.brand_max = 0.01;
        this.setting.total_core = 3;
        this.setting.max_particle = 10;
        this.setting.max_epoch = 5000;

        this.generateDataset();
        this.pso = new PSOP2(this.setting, this.gen);
        pso.initializeSwarm();
        pso.updateSwarmFitness();
        while(!pso.isConditionSatisfied())
        {
            pso.updateAllParticlePBest();
            pso.assignGBest();
            pso.evaluateAllParticle();
            pso.updateStoppingCondition();
            final ParticleP2 suspect = pso.particles[0];
            Data.replacePositon(suspect.data.positions, pso.gBest.positions);
            pso.calculateFitness(suspect);
            double aFitness = suspect.data.fitness;
            pso.repairData(suspect);
            pso.calculateFitness(suspect);
            double  bFitness   = suspect.data.fitness;
            boolean isNotError = (aFitness == bFitness);
            if(!isNotError)
            {
                for(int i = -1, is = pso.gBest.positions.length; ++i < is; )
                {
                    System.out.printf("Position.replace(suspectPos[%d], new Position(new int[] %s));\n", i, Arrays.toString(pso.gBest.positions[i].position).replace('[', '{').replace(']', '}'));
                }
                System.out.printf("%f %f = %b\n", aFitness, bFitness, isNotError);
                break;
            }
        }
    }

    @Test public void getRepairErrorFormattedReadyWithAutomation()
    {
        this.setting.bglob_min = 0.4;
        this.setting.bglob_max = 0.6;
        this.setting.bloc_min = 0.7;
        this.setting.bloc_max = 0.9;
        this.setting.brand_min = 0.001;
        this.setting.brand_max = 0.01;
        this.setting.total_core = 3;
        this.setting.max_particle = 10;
        this.setting.max_epoch = 5000;

        this.generateDataset();
        this.pso = new PSOP2(this.setting, this.gen);
        pso.initializeSwarm();
        pso.updateSwarmFitness();
        while(!pso.isConditionSatisfied())
        {
            pso.updateAllParticlePBest();
            pso.assignGBest();
            pso.evaluateAllParticle();
            pso.updateStoppingCondition();
            final ParticleP2 suspect = pso.particles[0];
            Data.replacePositon(suspect.data.positions, pso.gBest.positions);
            pso.calculateFitness(suspect);
            double aFitness = suspect.data.fitness;
            pso.repairData(suspect);
            pso.calculateFitness(suspect);
            double  bFitness   = suspect.data.fitness;
            boolean isNotError = (aFitness == bFitness);
            if(!isNotError)
            {
                final Position[] copy = new Position[pso.gBest.positions.length];
                for(int i = -1, is = copy.length; ++i < is; )
                {
                    copy[i] = Position.newInstance(Arrays.copyOf(pso.gBest.positions[i].position, pso.gBest.positions[i].position.length));
                }
                this.checkErrorConsistency(copy);
                break;
            }
        }
    }

    @Test public void checkErrorConsistency()
    {
        Position[] positions = new Position[5];
        positions[0] = new Position(new int[] {690, 689, 648, 583, 549, 651, 645, 0, 586, 547, 546, 650, 653, 585, 647, 0, 550, 548, 649, 646, 691, 584, 0, 652, 0, 587, 0, 686, 0, 0, 688, 644, 687, 0, 0, 0, 0, 0, 0, 0, 0, 0, 588, 0, 0, 0, 0});
        positions[1] = new Position(new int[] {684, 580, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 581, 579, 685, 0, 582, 683, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 682, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        positions[2] = new Position(new int[] {250, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 257, 252, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 256, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 258, 253, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 259, 251, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        positions[3] = new Position(new int[] {304, 307, 306, 308, 79, 23, 12, 0, 310, 82, 313, 300, 302, 314, 305, 0, 73, 10, 312, 3, 309, 301, 303, 0, 26, 13, 71, 75, 19, 70, 81, 0, 9, 5, 24, 14, 74, 4, 311, 17, 11, 7, 20, 8, 21, 80, 0, 18, 83, 27, 1, 15, 25, 2, 0, 77, 6, 78, 72, 22, 0, 0, 0, 0, 0, 0, 0, 84, 0, 0, 76, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16});
        positions[4] = new Position(new int[] {97, 120, 638, 566, 555, 210, 60, 438, 386, 65, 327, 553, 222, 0, 110, 223, 185, 87, 293, 665, 328, 41, 617, 207, 0, 225, 489, 348, 476, 0, 396, 561, 0, 517, 468, 0, 632, 163, 567, 363, 434, 122, 466, 280, 625, 639, 382, 0, 395, 50, 297, 412, 98, 428, 294, 296, 274, 453, 409, 140, 0, 427, 260, 341, 558, 0, 64, 661, 90, 282, 334, 345, 271, 91, 543, 445, 0, 170, 184, 658, 211, 0, 329, 284, 291, 654, 0, 450, 452, 568, 275, 413, 178, 670, 604, 149, 173, 332, 662, 339, 0, 151, 355, 574, 213, 123, 358, 441, 0, 448, 119, 51, 491, 0, 668, 551, 0, 276, 264, 349, 394, 0, 62, 104, 405, 673, 0, 609, 419, 0, 672, 356, 677, 117, 208, 263, 449, 433, 393, 383, 641, 482, 212, 416, 506, 0, 443, 655, 0, 423, 158, 230, 106, 559, 564, 542, 362, 440, 603, 417, 0, 86, 262, 505, 226, 219, 96, 477, 379, 331, 99, 164, 391, 0, 283, 607, 0, 565, 52, 0, 146, 102, 0, 347, 40, 451, 169, 455, 518, 261, 392, 414, 227, 509, 168, 0, 160, 475, 0, 39, 520, 0, 344, 112, 0, 105, 384, 446, 55, 664, 326, 0, 408, 150, 292, 95, 232, 521, 0, 385, 534, 0, 101, 676, 675, 526, 100, 134, 167, 342, 142, 357, 165, 594, 350, 218, 435, 68, 58, 172, 61, 180, 144, 159, 325, 0, 560, 0, 418, 63, 107, 524, 0, 454, 249, 485, 444, 380, 674, 0, 372, 233, 628, 92, 0, 266, 410, 643, 0, 575, 527, 656, 563, 0, 366, 556, 613, 94, 0, 388, 401, 36, 402, 471, 221, 368, 397, 59, 381, 116, 680, 131, 0, 614, 162, 343, 130, 510, 361, 0, 624, 633, 193, 375, 333, 138, 481, 369, 69, 93, 415, 335, 484, 272, 111, 642, 0, 436, 598, 0, 176, 608, 663, 615, 406, 290, 121, 316, 154, 183, 354, 127, 370, 148, 483, 152, 390, 462, 246, 619, 371, 0, 235, 89, 618, 0, 166, 67, 525, 0, 532, 188, 597, 620, 346, 0, 147, 0, 198, 141, 196, 467, 315, 56, 336, 191, 442, 360, 569, 0, 28, 295, 426, 602, 0, 497, 0, 389, 590, 0, 407, 459, 38, 217, 367, 616, 460, 0, 108, 220, 403, 621, 636, 145, 486, 231, 194, 637, 179, 0, 492, 0, 592, 557, 640, 340, 33, 516, 0, 374, 153, 500, 118, 0, 267, 461, 124, 627, 234, 0, 562, 0, 469, 522, 0, 273, 128, 45, 279, 201, 400, 114, 137, 398, 321, 660, 537, 186, 630, 0, 540, 495, 545, 88, 143, 0, 66, 265, 126, 571, 511, 544, 463, 519, 281, 0, 181, 420, 330, 623, 228, 554, 535, 589, 32, 182, 247, 404, 115, 161, 0, 671, 499, 515, 132, 373, 320, 209, 359, 0, 113, 669, 0, 657, 387, 0, 596, 429, 0, 200, 125, 0, 135, 0, 47, 667, 0, 156, 508, 0, 634, 0, 626, 473, 0, 139, 0, 478, 552, 0, 378, 48, 595, 0, 573, 287, 0, 502, 0, 528, 0, 44, 474, 298, 480, 376, 601, 0, 277, 0, 0, 299, 174, 0, 190, 439, 0, 0, 0, 133, 0, 629, 322, 0, 425, 611, 0, 54, 538, 365, 57, 289, 487, 432, 318, 0, 337, 0, 0, 189, 503, 498, 240, 278, 541, 85, 202, 0, 237, 42, 37, 681, 177, 0, 578, 0, 421, 422, 224, 430, 631, 0, 678, 286, 0, 447, 529, 0, 570, 0, 192, 206, 0, 531, 431, 0, 679, 0, 53, 205, 215, 523, 0, 610, 0, 46, 199, 0, 599, 530, 493, 244, 351, 424, 353, 572, 0, 236, 195, 319, 0, 635, 29, 0, 239, 338, 612, 0, 472, 187, 0, 605, 0, 496, 539, 0, 504, 470, 0, 501, 0, 666, 323, 0, 576, 216, 0, 197, 0, 288, 437, 317, 155, 606, 0, 0, 248, 533, 242, 204, 0, 0, 591, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 577, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 494, 0, 0, 0, 0, 0, 241, 0, 0, 0, 30, 507, 0, 0, 0, 0, 0, 0, 0, 600, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 229, 0, 0, 0, 411, 0, 0, 593, 0, 0, 0, 0, 0, 0, 0, 0, 103, 0, 0, 0, 0, 0, 0, 622, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 512, 0, 0, 0, 0, 0, 513, 0, 0, 0, 0, 0, 0, 488, 0, 0, 0, 0, 0, 0, 203, 0, 0, 0, 0, 0, 0, 536, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 399, 0, 0, 0, 514, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 659, 0, 0, 0, 0, 0, 0, 0, 0, 0, 31, 490, 0, 175, 0, 0, 0, 0, 0, 0, 0, 377, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 457, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 456, 0, 0, 0, 136, 0, 0, 0, 0, 34, 324, 157, 0, 0, 0, 171, 0, 0, 269, 0, 0, 0, 465, 0, 0, 0, 0, 49, 0, 0, 0, 0, 0, 0, 0, 0, 479, 0, 0, 0, 458, 0, 268, 0, 0, 0, 0, 0, 43, 0, 0, 0, 0, 214, 0, 0, 35, 464, 0, 0, 0, 238, 364, 270, 0, 0, 285, 0, 109, 243, 352, 0, 0, 0, 0, 0, 129, 0, 0, 0, 245});
        this.checkErrorConsistency(positions);
    }

    public void checkErrorConsistency(final Position[] positions)
    {
        this.setting.bglob_min = 0.4;
        this.setting.bglob_max = 0.6;
        this.setting.bloc_min = 0.7;
        this.setting.bloc_max = 0.9;
        this.setting.brand_min = 0.001;
        this.setting.brand_max = 0.01;
        this.setting.total_core = 3;
        this.setting.max_particle = 10;
        this.setting.max_epoch = 5000;

        this.generateDataset();
        this.pso = new PSOP2(this.setting, this.gen);
        pso.initializeSwarm();
        pso.updateSwarmFitness();
        final ParticleP2 suspect    = pso.particles[0];
        final Position[] suspectPos = suspect.data.positions;
        for(int i = -1, is = suspectPos.length; ++i < is; )
        {
            Position.replace(suspectPos[i], positions[i]);
        }
        final Position[] copy = new Position[suspect.data.positions.length];
        for(int i = -1, is = copy.length; ++i < is; )
        {
            copy[i] = Position.newInstance(Arrays.copyOf(suspect.data.positions[i].position, suspect.data.positions[i].position.length));
        }
        pso.calculateFitness(suspect);
        System.out.printf("Fitness              : %f\n", suspect.data.fitness);
        pso.repairData(suspect);
        pso.calculateFitness(suspect);
        System.out.printf("Fitness After Repair : %f\n", suspect.data.fitness);
        Data.replacePositon(suspect.data.positions, copy);
        pso.calculateFitness(suspect);
        int    error   = 0;
        double fitness = suspect.data.fitness;
        for(int i = -1, is = 1000; ++i < is; )
        {
            pso.calculateFitness(suspect);
            error += (fitness == suspect.data.fitness ? 0 : 1);
        }
        System.out.printf("Test Calculate Fitness Error : %d\n", error);
        Assert.assertEquals(0, error);
        int[] arraymap = new int[copy.length];
        error = 0;
        for(int i = -1, is = copy.length; ++i < is; )
        {
            final boolean isSame = Arrays.equals(suspect.data.positions[i].position, copy[i].position);
            error += (isSame ? 0 : 1);
            arraymap[i] = (isSame ? 1 : 0);
        }
        System.out.printf("Is particle stable = %3s : %s\n", error == 0 ? "yes" : "no", Arrays.toString(arraymap));
        if(error == 0)
        {
            System.out.println("Begin Repair Phase");
            Data.replacePositon(suspect.data.positions, copy);
            pso.calculateFitness(suspect);
            System.out.printf("Fitness              : %f\n", suspect.data.fitness);
            pso.repairData(suspect);
            pso.calculateFitness(suspect);
            System.out.printf("Fitness After Repair : %f\n", suspect.data.fitness);
            error = 0;
            fitness = suspect.data.fitness;
            for(int i = -1, is = 1000; ++i < is; )
            {
                pso.calculateFitness(suspect);
                error += (fitness == suspect.data.fitness ? 0 : 1);
            }
            System.out.printf("Test Calculate Fitness Error After Repair : %d\n", error);
            Assert.assertEquals(0, error);
            error = 0;
            for(int i = -1, is = copy.length; ++i < is; )
            {
                final boolean isSame = Arrays.equals(suspect.data.positions[i].position, copy[i].position);
                error += (isSame ? 0 : 1);
                arraymap[i] = (isSame ? 1 : 0);
            }
            System.out.printf("Is particle unstable = %3s : %s\n", error > 0 ? "yes" : "no", Arrays.toString(arraymap));
            if(error > 0)
            {
                System.out.println("Begin Repair and Calculate Phase");
                Data.replacePositon(suspect.data.positions, copy);
                pso.calculateFitness(suspect);
                System.out.printf("Fitness              : %f\n", suspect.data.fitness);
                pso.repairData(suspect);
                pso.calculateFitness(suspect);
                System.out.printf("Fitness After Repair : %f\n", suspect.data.fitness);
                error = 0;
                fitness = suspect.data.fitness;
                for(int i = -1, is = 1000; ++i < is; )
                {
                    Data.replacePositon(suspect.data.positions, copy);
                    pso.repairData(suspect);
                    pso.calculateFitness(suspect);
                    error += (fitness == suspect.data.fitness ? 0 : 1);
                }
                System.out.printf("Test Calculate Fitness Error with Repairing first : %d\n", error);
                Assert.assertEquals(0, error);
                error = 0;
                for(int i = -1, is = copy.length; ++i < is; )
                {
                    final boolean isSame = Arrays.equals(suspect.data.positions[i].position, copy[i].position);
                    error += (isSame ? 0 : 1);
                    arraymap[i] = (isSame ? 1 : 0);
                }
                System.out.printf("Is particle unstable = %3s : %s\n", error > 0 ? "yes" : "no", Arrays.toString(arraymap));
                if(error > 0)
                {
                    System.out.println("Begin Repair Only Phase");
                    Data.replacePositon(suspect.data.positions, copy);
                    pso.calculateFitness(suspect);
                    System.out.printf("Fitness              : %f\n", suspect.data.fitness);
                    pso.repairData(suspect);
                    pso.calculateFitness(suspect);
                    System.out.printf("Fitness After Repair : %f\n", suspect.data.fitness);
                    error = 0;
                    final Position[] repairedPosition = new Position[suspect.data.positions.length];
                    for(int i = -1, is = repairedPosition.length; ++i < is; )
                    {
                        repairedPosition[i] = Position.newInstance(Arrays.copyOf(suspect.data.positions[i].position, suspect.data.positions[i].position.length));
                    }
                    for(int i = -1, is = 1000; ++i < is; )
                    {
                        Data.replacePositon(suspect.data.positions, copy);
                        pso.repairData(suspect);
                        int stableError = 0;
                        for(int j = -1, js = copy.length; ++j < js; )
                        {
                            stableError += (Arrays.equals(suspect.data.positions[j].position, repairedPosition[j].position) ? 0 : 1);
                        }
                        error += (stableError == 0 ? 0 : 1);
                    }
                    System.out.printf("Test Repair Only Error : %d\n", error);
                    Assert.assertEquals(0, error);
                }
            }
        }
    }
}